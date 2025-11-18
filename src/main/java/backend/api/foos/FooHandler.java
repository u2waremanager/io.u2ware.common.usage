package backend.api.foos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Foo;

@Component
@RepositoryEventHandler
public class FooHandler {

    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(Foo e)throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Foo e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Foo e)throws Exception{
        logger.info("@HandleBeforeSave : "+e);
    }

    // @HandleBeforeLinkDelete
    // public void HandleBeforeLinkDelete(Foo e)throws Exception{
    //     logger.info("@HandleBeforeLinkDelete : "+e);
    // }

    // @HandleBeforeLinkSave
    // public void HandleBeforeLinkSave(Foo e)throws Exception{
    //     logger.info("@HandleBeforeLinkSave : "+e);
    // }


    @HandleAfterCreate
    public void HandleAfterCreate(Foo e) throws Exception{
        logger.info("@HandleAfterCreate : "+e);
    }

    @HandleAfterDelete
    public void HandleAfterDelete(Foo e) throws Exception{
        logger.info("@HandleAfterDelete : "+e);
    }

    @HandleAfterSave
    public void HandleAfterSave(Foo e)throws Exception{
        logger.info("@HandleAfterSave : "+e);
    }



}
