package backend;

import java.io.IOException;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class ApplicationWebConfig implements WebMvcConfigurer {

    protected Log logger = LogFactory.getLog(getClass());

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(false)
                .maxAge(30000);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(30000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        logger.info("addResourceHandlers "+registry);

        registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/")
                    .setCachePeriod(20)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath, Resource location) throws IOException {


                            logger.info("addResourceHandlers "+resourcePath);


                            Resource r = location.createRelative(resourcePath);
                            if(r.exists() && r.isReadable()){
                                return r;
                            } else{

                                //////////////////////////////
                                // Vue has spacialial uri
                                //////////////////////////////
                                // if(.....){
                                //     return new ClassPathResource("/io/u2ware/ocpp/index.html");
                                // }else{
                                //     return null;
                                // }

                                //////////////////////////////
                                // Vue has all uri
                                //////////////////////////////
                                return new ClassPathResource("/META-INF/resources/index.html");
                            }
                        }
                    });
    }
    
}