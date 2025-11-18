package backend.api.users;


import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import backend.domain.User;
import backend.domain.properties.AttributesSet;
import io.u2ware.common.oauth2.webmvc.AuthenticationContext;


@Component
public class UserService implements Converter<Jwt, Collection<GrantedAuthority>> {

    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        logger.info("token convert started: "+jwt.getSubject());
        AttributesSet roles = new AttributesSet();

        Collection<GrantedAuthority> authorities = AuthenticationContext.authorities(jwt);
        for(GrantedAuthority a : authorities){
            roles.add(a.getAuthority());
        }
        logger.info("jwtAuthorities : "+authorities);


        ////////////////////////////////
        //
        ////////////////////////////////
        try{
            userRepository.findById(jwt.getSubject()).ifPresentOrElse((u)->{
                roles.addAll(u.getRoles());
     
            }, ()->{
                if(userRepository.count() == 0l){
                    roles.add("ROLE_ADMIN");
                }else if(roles.size() == 0 ) {
                    roles.add("ROLE_USER");
                }
                User u = new User();
                u.setUserId(jwt.getSubject());
                u.setRoles(roles);
                //For Auditing...
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
                //For Auditing...
                userRepository.save(u);
            });

        }catch(Exception e){
            e.printStackTrace();
        }

        ////////////////////////////////
        //
        ////////////////////////////////
        for(Object role : roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            if(! authorities.contains(authority)) {
                authorities.add(authority);
            }
        }
        logger.info("securityAuthorities : "+authorities);
        return authorities;
    }
}
