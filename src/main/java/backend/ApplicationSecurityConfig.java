package backend;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.u2ware.common.oauth2.jwt.OAuth2ResourceServerAdministration;
import io.u2ware.common.oauth2.jwt.OAuth2ResourceServerUserinfoService;





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
    public SecurityFilterChain filterChain(HttpSecurity http, OAuth2ResourceServerAdministration admin) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize

                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()  
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            )
            .formLogin(formLogin -> formLogin
                .defaultSuccessUrl("/oauth2/logon") 
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/oauth2/logoff")
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
	private @Autowired SecurityProperties sp;
	private @Autowired OAuth2ResourceServerProperties op;

    private @Autowired OAuth2ResourceServerUserinfoService userinfo;
    private @Autowired Converter<Jwt, Collection<GrantedAuthority>> converter;

    @Bean
    public OAuth2ResourceServerAdministration oauth2ResourceServerAdministration() {
        return new OAuth2ResourceServerAdministration(sp, op, userinfo);
    }


    /////////////////////////////////////////////////////////
    // 
    /////////////////////////////////////////////////////////
	@Bean
	public UserDetailsService userDetailsService(OAuth2ResourceServerAdministration admin) {
		return admin.userDetailsService();
	}

    @Bean
    public JwtEncoder jwtEncoder(OAuth2ResourceServerAdministration admin) throws Exception{
        return admin.jwtEncoder();
    }

    @Bean 
    public JwtDecoder jwtDecoder(OAuth2ResourceServerAdministration admin) throws Exception{
        return admin.jwtDecoder();
    }

    @Bean 
    public JwtAuthenticationConverter jwtAuthenticationConverter(OAuth2ResourceServerAdministration admin) {
        return admin.jwtConverter(converter);
    }


}