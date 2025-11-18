package backend.api.items;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Item;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class ItemHandler {
    
    protected Log logger = LogFactory.getLog(getClass());



    @HandleBeforeCreate
    public void HandleBeforeCreate(Item e) throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
        // throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleBeforeSave
    public void HandleBeforeSave(Item e)throws Exception{
        logger.info("@HandleBeforeSave : "+e);

        // if(AuthenticationContext.hasAuthorities("ROLE_ADMIN")) return;
        // logger.info("@HandleBeforeSave1 : ");

        // throw ResponseStatusExceptions.UNAUTHORIZED;
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Item e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);

        // if(AuthenticationContext.hasAuthorities("ROLE_ADMIN")) return;
        // logger.info("@HandleBeforeDelete : ");

        // throw ResponseStatusExceptions.UNAUTHORIZED;
    }



    @HandleAfterRead
    public void HandleAfterRead(Item e, Serializable r)throws Exception{
        logger.info("@HandleAfterRead : "+e);
        logger.info("@HandleAfterRead : "+r);

        // // Authentication authentication = AuthenticationContext.authentication();
        // // logger.info("@HandleAfterRead1 : "+authentication);

        // // Collection<GrantedAuthority> authorities = AuthenticationContext.authorities();
        // // logger.info("@HandleAfterRead2 : "+e.getUpdated());

        // if(AuthenticationContext.hasAuthorities("ROLE_ADMIN")) return;
        // // logger.info("@HandleAfterRead3 : "+authorities);

        // if(AuthenticationContext.hasAuthorities(e.getOrganization())) return;
        // // logger.info("@HandleAfterRead4 : "+e.getItemGroup());
        
        // if(AuditedAuditor.hasCurrentUsername(e.getInserted())) return;
        // // logger.info("@HandleAfterRead5 : "+e.getInserted());
        
        // throw ResponseStatusExceptions.UNAUTHORIZED;
    }


    @HandleBeforeRead
    public void HandleBeforeRead(Item e, Specification<Item> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        logger.info("@HandleBeforeRead : "+r);
    }
}
