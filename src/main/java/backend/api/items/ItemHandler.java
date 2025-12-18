package backend.api.items;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Bar;
import backend.domain.Foo;
import backend.domain.Item;
import backend.domain.properties.LinkConversion;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class ItemHandler {
    
    protected Log logger = LogFactory.getLog(getClass());

    private @Autowired LinkConversion linkConversion;

	private void conversion(Item e) throws Exception{
		logger.info("conversion1 "+e.getFooLink());
		logger.info("conversion1 "+e.getBarsLinks());

		linkConversion.convertWithEntity(Foo.class, e.getFooLink(), ref->{e.setFoo(ref);});
        linkConversion.convertWithEntity(Bar.class, e.getBarsLinks(), ref->{e.setBars(ref);});
	}



    @HandleBeforeCreate
    public void HandleBeforeCreate(Item e) throws Exception{
        conversion(e);
		logger.info("@HandleBeforeCreate "+e.getFoo());
		logger.info("@HandleBeforeCreate "+e.getFooLink());
    }


    @HandleBeforeSave
    public void HandleBeforeSave(Item e)throws Exception{
        conversion(e);
		logger.info("@HandleBeforeSave "+e.getFoo());
		logger.info("@HandleBeforeSave "+e.getFooLink());
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Item e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
    }



    // @HandleAfterRead
    // public void HandleAfterRead(Item e, Serializable r)throws Exception{
    //     logger.info("@HandleAfterRead : "+e);
    //     logger.info("@HandleAfterRead : "+r);
    // }


    @HandleBeforeRead
    public void HandleBeforeRead(Item e, Specification<Item> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        logger.info("@HandleBeforeRead : "+r);
    }
}
