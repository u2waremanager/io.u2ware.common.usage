package backend.domain;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.u2ware.common.docs.RestMockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class U2wareCommonDocsTests {

    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected MockMvc mvc;


    @Autowired
    protected ObjectMapper objectMapper;


    @Test
    void contextLoads() throws Exception {

        mvc
            .perform(
                MockMvcRequestBuilders.get("/api")
                        .with((r)->{
                            r.addHeader("hello1", "world1");
                            return r;
                        })
                        .header("h1", "v1")
                // .content(objectMapper.writeValueAsString(f1))
                // .with(SecurityMockMvcRequestPostProcessors.user("tester"))
            )
            .andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful()
            )
            .andDo(
                MockMvcResultHandlers.print(System.err)
            );


        mvc
            .perform(
                    RestMockHttpServletRequestBuilder.get(new URI("/api"))
                            .with((r)->{
                                r.addHeader("hello2", "world2");
                            })
                            .header("h2", "v2")
            )
            .andExpect(
                    MockMvcResultMatchers.status().is2xxSuccessful()
            )
            .andDo(
                    MockMvcResultHandlers.print(System.err)
            )
        ;

    }



}