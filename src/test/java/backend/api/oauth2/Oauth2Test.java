package backend.api.oauth2;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Oauth2Test {
    
    protected Log logger = LogFactory.getLog(getClass());

    private @Autowired MockMvc mvc;
    private @Autowired Oauth2Docs od;
    
    
	@SuppressWarnings("null")
    @Test
	void contextLoads() throws Exception{

        Jwt t1 = od.jose("user");
        Jwt t2 = od.jose("user");
        Jwt t3 = od.jose("user1");
       

        Assertions.assertEquals(t1.getTokenValue(), t2.getTokenValue());
        Assertions.assertNotEquals(t1.getTokenValue(), t3.getTokenValue());


		///////////////////////////////////
		// /oauth2/userinfo
		///////////////////////////////////
		mvc.perform(get("/oauth2/userinfo"))
            .andExpect(is4xx());


        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").security("aa"))
            .andExpect(is4xx());

        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").security(od::jwt, "aa"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList()));
        
        mvc.perform(get("/oauth2/userinfo").security(od::jwt, "aa", "AA"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_AA")));

        mvc.perform(get("/oauth2/userinfo").security(od::jwt, "aa", new String[]{"BB", "CC"}))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_BB", "ROLE_CC")));

        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").security(od::jose, "aa"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList()));
        
        mvc.perform(get("/oauth2/userinfo").security(od::jose, "aa", "AA"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_AA")));

        mvc.perform(get("/oauth2/userinfo").security(od::jose, "aa", new String[]{"BB", "CC"}))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_BB", "ROLE_CC")));


        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").auth("aa"))
            .andExpect(is4xx());

        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").auth(od::jwt, "aa"))
            .andExpect(is4xx());

        ////////////////////////////////////
        mvc.perform(get("/oauth2/userinfo").auth(od::jose, "aa", "ADMIN"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_ADMIN")))
            ;

        mvc.perform(get("/oauth2/userinfo").auth(od::jose, "bb", "USER"))
            .andExpect(is2xx())
            .andExpect(isJson("$.claims.authorities", Arrays.asList("ROLE_USER")))
            ;

    }
}
