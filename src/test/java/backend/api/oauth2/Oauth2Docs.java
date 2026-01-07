package backend.api.oauth2;


import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import io.u2ware.common.docs.MockMvcRestDocs;
import io.u2ware.common.oauth2.jose.JoseKeyEncryptor;


@Component
public class Oauth2Docs extends MockMvcRestDocs {

    
    protected @Autowired(required = false) JwtEncoder jwtEncoder;
    protected @Autowired(required = false) JwtDecoder jwtDecoder;


    public Jwt jwt(String username, String... authorities) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("email", username);
        claims.put("name", username);
        if(! ObjectUtils.isEmpty(authorities)){
            claims.put("authorities", Arrays.asList(authorities));
        }
        claims.put("hello", "jwt");

        Jwt jwt = new Jwt(
                username,
                Instant.now(),
                Instant.now().plusSeconds(30),
                Map.of("alg", "none"),
                claims
        );
        return jwt;
    }

    
    public Jwt jose(String username, String... authorities) {

        try{
            return JoseKeyEncryptor.encrypt(jwtEncoder, claims->{

                claims.put("sub", username);
                claims.put("email", username);
                claims.put("name", username);
                claims.put("hello", "jose");
                if(! ObjectUtils.isEmpty(authorities)){
                    claims.put("authorities", Arrays.asList(authorities));
                }
            });
    
        }catch(Exception e){
            return null;
        }
    }


    public String token(String username) throws Exception {

        try{
            ClassPathResource c = new ClassPathResource("public.txt");
            Path p = c.getFile().toPath();

            List<String> lines = Files.readAllLines(p);
            for(String line : lines) {
                if(StringUtils.hasText(line)) {
                    Jwt jwt = JoseKeyEncryptor.decrypt(jwtDecoder, ()-> line);

                    if(jwt.getSubject().equals(username)) {
                        return line;
                    }
                }
            }   
            return null;

        }catch(Exception e){
            return null;
        }
    } 

    
}
