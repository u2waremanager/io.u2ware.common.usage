package backend.api.users;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;


@Component
public class UserDocs extends MockMvcRestDocs {


    public Map<String,Object> newEntity(){
        Map<String,Object> r = new HashMap<>();
        return r;
    }


    public Map<String,Object> modifyEntity(String userId){
        Map<String,Object> r = context(userId);
        r.put("userTimestamp", System.currentTimeMillis());
        return r;
    }

    



    public Map<String,Object> searchEntity(){

        Map<String,Object> r = new HashMap<>();

        r.put("age", 111);
        return r;
    }
    
}
