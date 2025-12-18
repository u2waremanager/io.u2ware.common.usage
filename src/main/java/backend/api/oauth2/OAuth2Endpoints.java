package backend.api.oauth2;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import backend.api.users.UserRepository;
import backend.domain.User;
import io.u2ware.common.oauth2.webmvc.AuthenticationContext;


@BasePathAwareController
@RestController
public class OAuth2Endpoints {
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired UserRepository userRepository;

    @GetMapping(value = "/oauth2/userinfo")
    public @ResponseBody ResponseEntity<Object> oauth2UserInfo(Authentication authentication) {

        Jwt jwt = AuthenticationContext.authenticationToken(authentication);
        String id = jwt.getSubject();
        Optional<User> user = userRepository.findById(id);
        
        if(user.isPresent())  {
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }          
   }
}