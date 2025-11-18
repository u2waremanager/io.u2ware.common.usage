package backend.api.oauth2;


import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import io.u2ware.common.docs.MockMvcRestDocs;
import io.u2ware.common.oauth2.crypto.JoseEncryptor;


@Component
public class Oauth2Docs extends MockMvcRestDocs {

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
            return JoseEncryptor.getInstance().encrypt(claims->{

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
    
}
