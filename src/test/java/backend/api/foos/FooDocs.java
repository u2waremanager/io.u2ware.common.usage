package backend.api.foos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;
import io.u2ware.common.docs.RestDocumentationResultHandlerBuilder;


@Component
public class FooDocs extends MockMvcRestDocs {

    public Map<String,Object> newEntity(String id){

        Map<String,Object> r = new HashMap<>();
        r.put("id", id);
        r.put("name", super.randomText("Foo-"));
        r.put("age", super.randomInt());
        return r;
    }

    public Map<String,Object> searchEntity(){

        Map<String,Object> r = new HashMap<>();

        r.put("age", 111);
        return r;
    }




    public void searchDocs(RestDocumentationResultHandlerBuilder b) {

    }
}
