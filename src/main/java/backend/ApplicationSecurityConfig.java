package backend;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import io.u2ware.common.oauth2.config.EnableAuthorizationEndpoints;
import io.u2ware.common.oauth2.crypto.JoseEncryptor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableAuthorizationEndpoints
public class ApplicationSecurityConfig {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors-> cors
                    .configurationSource(corsConfigurationSource)
            )
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
                    ))

            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/oauth2/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/profile/**").permitAll()
                    // .requestMatchers("/api/foos/**").permitAll()
                    // .requestMatchers("/api/bars/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            )
            .oauth2ResourceServer(
                    oauth2->oauth2.jwt(Customizer.withDefaults())
           );
        ;
        return http.build();
    }


    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver r = new DefaultBearerTokenResolver();
        r.setAllowUriQueryParameter(true); //?access_token GET only
        return r;
    }

    @Autowired(required = false)
    private Converter<Jwt, Collection<GrantedAuthority>> customJwtGrantedAuthoritiesConverter;
    
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = null;
        if(customJwtGrantedAuthoritiesConverter == null) {
            JwtGrantedAuthoritiesConverter c = new JwtGrantedAuthoritiesConverter();
            c.setAuthoritiesClaimName("authorities");
            c.setAuthorityPrefix("ROLE_");
            jwtGrantedAuthoritiesConverter = c;
        }else{
            jwtGrantedAuthoritiesConverter = customJwtGrantedAuthoritiesConverter;
        }

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:}")
    private String jwkSetUri;

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        try{
            return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        }catch(Exception e){
            return JoseEncryptor.getInstance().decoder();
        }
    }




}
