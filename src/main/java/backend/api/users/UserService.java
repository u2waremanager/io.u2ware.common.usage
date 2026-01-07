package backend.api.users;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import io.u2ware.common.oauth2.jwt.AuthenticationContext;
import io.u2ware.common.oauth2.jwt.OAuth2ResourceServerUserinfoService;


@Component
public class UserService implements Converter<Jwt, Collection<GrantedAuthority>>, OAuth2ResourceServerUserinfoService{


    protected Log logger = LogFactory.getLog(getClass());


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        logger.info("convert");

        return AuthenticationContext.authorities(jwt);
    }


    @Override
    public Object loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername: "+username);


        return null;

    }
    
}
