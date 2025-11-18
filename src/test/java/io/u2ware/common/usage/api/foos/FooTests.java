package io.u2ware.common.usage.api.foos;

import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.is4xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import io.u2ware.common.usage.api.oauth2.Oauth2Docs;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class FooTests {


	protected Log logger = LogFactory.getLog(getClass());


	protected @Autowired MockMvc mvc;

	protected @Autowired Oauth2Docs od;	
	protected @Autowired FooDocs fd;


	@SuppressWarnings("null")
	@Test
	void contextLoads1() throws Exception{
		
        Jwt u = od.jose("u");

		mvc.perform(get("/api/profile/foos")).andExpect(is2xx()).andDo(print());

		//////////////////////////////////////////////
		// CrudRepository
		//////////////////////////////////////////////
		mvc.perform(get("/api/foos")).andExpect(is4xx());             // unauthorized
		mvc.perform(get("/api/foos").auth(u)).andExpect(is2xx());     // ok

		mvc.perform(get("/api/foos/search")).andExpect(is4xx());          // unauthorized
		mvc.perform(get("/api/foos/search").auth(u)).andExpect(is2xx());  // ok


		mvc.perform(post("/api/foos").content(fd::newEntity, "a")).andExpect(is4xx());
		mvc.perform(post("/api/foos").auth(u).content(fd::newEntity, "a")).andExpect(is2xx())
			.andDo(result(fd::context, "f1"));


		String uri = fd.context("f1", "$._links.self.href");
		mvc.perform(get(uri)).andExpect(is4xx());             // unauthorized
		mvc.perform(get(uri).auth(u)).andExpect(is2xx());     // ok



		//////////////////////////////////////////////
		// RestfulJpaRepository
		//////////////////////////////////////////////
		mvc.perform(post("/api/foos/search")).andExpect(is4xx());          // unauthorized
		mvc.perform(post("/api/foos/search").auth(u)).andExpect(is4xx());  // not supported

		mvc.perform(post(uri)).andExpect(is4xx());             // unauthorized
		mvc.perform(post(uri).auth(u)).andExpect(is4xx());     // not supported




	}



}
