package backend.api.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import backend.domain.User;
import io.u2ware.common.oauth2.jwt.AuthenticationContext;
import io.u2ware.common.oauth2.jwt.OAuth2ResourceServerUserinfoService;


@Component
public class UserService implements Converter<Jwt, Collection<GrantedAuthority>>, OAuth2ResourceServerUserinfoService {


    protected Log logger = LogFactory.getLog(getClass());

   
    protected @Autowired SecurityProperties securityProperties;
    protected @Autowired UserRepository userRepository;
    protected @Autowired(required = false) PasswordEncoder passwordEncoder;


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        logger.info("convert ");
        Collection<GrantedAuthority> authorities = AuthenticationContext.authorities(jwt);
        logger.info("authorities By jwt:    "+authorities);


        userRepository.findById(jwt.getSubject()).ifPresentOrElse((u)->{

            Collection<GrantedAuthority> domainAuthorities = u.getAuthorities();
            logger.info("authorities By domain: "+domainAuthorities);
            authorities.addAll(domainAuthorities);
    
        }, ()->{
            Collection<GrantedAuthority> domainAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            logger.info("authorities By domain: "+domainAuthorities);
            authorities.addAll(domainAuthorities);

            //
            User u = new User();
            u.setUsername(jwt.getSubject());
            u.setAuthorities(domainAuthorities);
            //For Auditing...
            // SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
            userRepository.save(u);
            //
        });

        logger.info("authorities result : "+authorities);
        return authorities;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername1111: "+username);

        Optional<User> user = userRepository.findById(username);
        if(user.isPresent()) return user.get();


        String rootUser = this.securityProperties.getUser().getName();
        if(! rootUser.equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        String password = this.securityProperties.getUser().getPassword();
        String rootPassword = passwordEncoder != null ? passwordEncoder.encode(password) : "{noop}"+password;
        logger.info("loadUserByUsername1111: "+passwordEncoder);
        logger.info("loadUserByUsername1111: "+rootPassword);


        User u = new User();
        u.setUsername(rootUser);
        u.setPassword(rootPassword);
        u.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userRepository.findById(username).map(r->r).orElse(userRepository.save(u));
    }
}