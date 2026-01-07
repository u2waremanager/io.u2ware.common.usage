package backend;

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import backend.config.OAuth2ResourceServerConfigurationSupport;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

	protected Log logger = LogFactory.getLog(getClass());

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(30000l);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> 
                    authorize
                        .requestMatchers(HttpMethod.GET, "/api").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profile/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth2->oauth2
                .jwt(Customizer.withDefaults())
            )
            
            ;
        
        return http.build();
    }


    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver r = new DefaultBearerTokenResolver();
        r.setAllowUriQueryParameter(true); //?access_token GET only
        return r;
    }


    /////////////////////////////////////////////////////////
    // OAuth2ResourceServerConfigurationSupport
    /////////////////////////////////////////////////////////
	private @Autowired SecurityProperties securityProperties;
	private @Autowired OAuth2ResourceServerProperties oauth2ResourceServerProperties;

    @Bean
    public OAuth2ResourceServerConfigurationSupport oauth2ResourceServerConfigurationSupport() {
        return new OAuth2ResourceServerConfigurationSupport(securityProperties, oauth2ResourceServerProperties);
    }


    /////////////////////////////////////////////////////////
    // 
    /////////////////////////////////////////////////////////
    @Bean
    public JwtEncoder jwtEncoder(OAuth2ResourceServerConfigurationSupport support) throws Exception{
        return support.jwtEncoder();
    }

    @Bean 
    public JwtDecoder jwtDecoder(OAuth2ResourceServerConfigurationSupport support) throws Exception{
        return support.jwtDecoder();
    }

    @Bean 
    public JwtAuthenticationConverter jwtAuthenticationConverter(OAuth2ResourceServerConfigurationSupport support) {
        return support.jwtConverter();
    }

	@Bean
	public UserDetailsService userDetailsService(OAuth2ResourceServerConfigurationSupport support) {
		return support.userDetailsService();
	}

}