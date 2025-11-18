package backend.domain.auditing;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import io.u2ware.common.oauth2.webmvc.AuthenticationContext;

@Component
public class AuditedAuditor implements AuditorAware<Audited> {

    protected Log logger = LogFactory.getLog(getClass());

    @Override
    public Optional<Audited> getCurrentAuditor() {

        logger.info("getCurrentAuditor");
        logger.info("getCurrentAuditor");
        logger.info("getCurrentAuditor");
        logger.info("getCurrentAuditor");
        logger.info("getCurrentAuditor");


        Audited entity = new Audited();
        entity.setUsername(AuditedAuditor.getCurrentUsername());
        entity.setTimestamp(AuditedAuditor.getCurrentTimestamp());
        entity.setAddress(AuditedAuditor.getCurrentAddress());
        return Optional.of(entity);
    }


    public static String getCurrentUsername(){
        try{
            return AuthenticationContext.authenticationToken().getSubject();
        }catch(Exception e){
            return null;
        }
    }

    public static Long getCurrentTimestamp(){
        try{
            return System.currentTimeMillis();
        }catch(Exception e){
            return null;
        }
    }

    public static String getCurrentAddress(){
        try{
            return AuthenticationContext.httpServletRequest().getRemoteAddr();
        }catch(Exception e){
            return null;
        }
    }


    //////////////////////////////////////////////////
    //
    //////////////////////////////////////////////////
    public static boolean isOwner(Audited audited){
        try{
            return audited.getUsername().equals(getCurrentUsername());
        }catch(Exception e){
            return false;
        }
    }
    public static boolean hasPermission(String... roles){
        if(roles.length == 0) {
            return ! AuthenticationContext.isAnonymousUser();
        }
        return AuthenticationContext.hasAuthorities(roles);
    }
    public static boolean hasPermission(Audited audited, String... roles){
        return AuthenticationContext.hasAuthorities(roles) || isOwner(audited);
    }
    public static boolean hasNotPermission(String... roles){
        return ! hasPermission(roles);
    }
    public static boolean hasNotPermission(Audited audited, String... roles){
        return ! hasPermission(audited, roles);
    }    

}
