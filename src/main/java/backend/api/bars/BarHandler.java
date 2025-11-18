package backend.api.bars;


import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Bar;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BarHandler {

    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(Bar e)throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Bar e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Bar e)throws Exception{
        logger.info("@HandleBeforeSave : "+e);
    }

    // @HandleBeforeLinkDelete
    // public void HandleBeforeLinkDelete(Bar e)throws Exception{
    //     logger.info("@HandleBeforeLinkDelete : "+e);
    // }

    // @HandleBeforeLinkSave
    // public void HandleBeforeLinkSave(Bar e)throws Exception{
    //     logger.info("@HandleBeforeLinkSave : "+e);
    // }


    @HandleAfterCreate
    public void HandleAfterCreate(Bar e)throws Exception{
        logger.info("@HandleAfterCreate : "+e);
    }

    @HandleAfterDelete
    public void HandleAfterDelete(Bar e)throws Exception{
        logger.info("@HandleAfterDelete : "+e);
    }

    @HandleAfterSave
    public void HandleAfterSave(Bar e)throws Exception{
        logger.info("@HandleAfterSave : "+e);
    }



    @HandleAfterRead
    public void HandleAfterRead(Bar e, Serializable r)throws Exception{
        logger.info("@HandleAfterRead : "+e);
        logger.info("@HandleAfterRead : "+r);
    }


    @HandleBeforeRead
    public void HandleBeforeRead(Bar e, Specification<Bar> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        logger.info("@HandleBeforeRead : "+r);
    }

}

