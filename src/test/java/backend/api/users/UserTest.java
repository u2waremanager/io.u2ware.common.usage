package backend.api.users;


import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.api.oauth2.Oauth2Docs;
import backend.domain.User;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserTest {
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
    protected @Autowired UserDocs ud;
    protected @Autowired Oauth2Docs od;

    protected @Autowired UserRepository userRepository;
    
    @Test
    public void contextLoads() throws Exception{



        List<Object> roles = Arrays.asList("ROLE_ADMIN");


        Specification<User> spec = JpaSpecificationBuilder.of(User.class)
            .where()
            .and()
            .in("roles",  roles)
            .build();

        Optional<User> user = userRepository.findOne(spec);

        String admin = user.map(u-> u.getUserId()).orElse("admin" );



        Jwt u1 = od.jose(admin);
        Jwt u2 = od.jose("u2");
        Jwt u3 = od.jose("u3");
        Jwt u4 = od.jose("u4");
        

        mvc.perform(post("/api/users").auth(u1)).andExpect(is4xx());
        mvc.perform(post("/api/users").auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users").auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users").auth(u4)).andExpect(is4xx());


        mvc.perform(post("/api/users/"+admin)).andExpect(is4xx());
        mvc.perform(post("/api/users/"+admin).auth(u1)).andExpect(is2xx());
        mvc.perform(post("/api/users/"+admin).auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users/"+admin).auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users/"+admin).auth(u4)).andExpect(is4xx());


        // mvc.perform(post("/api/users/u2").auth(u1)).andExpect(is2xx());
        // mvc.perform(post("/api/users/u2").auth(u2)).andExpect(is2xx());
        // mvc.perform(post("/api/users/u2").auth(u3)).andExpect(is4xx());
        // mvc.perform(post("/api/users/u2").auth(u4)).andExpect(is4xx());

    }
}
