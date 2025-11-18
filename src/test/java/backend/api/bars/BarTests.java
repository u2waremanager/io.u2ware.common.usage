package backend.api.bars;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.api.oauth2.Oauth2Docs;

@SpringBootTest
@AutoConfigureMockMvc
public class BarTests {


	protected Log logger = LogFactory.getLog(getClass());

	protected @Autowired MockMvc mvc;

	protected @Autowired Oauth2Docs od;	
	protected @Autowired BarDocs bd;



	@Autowired
	protected BarRepository barRepository;

	@Test @SuppressWarnings("null")
	void contextLoads1() throws Exception{


        Jwt u = od.jose("u");


		mvc.perform(get("/api/profile/bars")).andExpect(is2xx()).andDo(print());


		//////////////////////////////////////////////
		// CrudRepository
		//////////////////////////////////////////////
		mvc.perform(get("/api/bars")).andExpect(is4xx());         // unauthorized
		mvc.perform(get("/api/bars").auth(u)).andExpect(is4xx()); // not supported

		mvc.perform(get("/api/bars/search")).andExpect(is4xx());          // unauthorized
		mvc.perform(get("/api/bars/search").auth(u)).andExpect(is4xx());  // not supported


		mvc.perform(post("/api/bars").content(bd::newEntity)).andExpect(is4xx());
		mvc.perform(post("/api/bars").auth(u).content(bd::newEntity)).andExpect(is2xx())
			.andDo(result(bd::context, "b1"));

		String uri = bd.context("b1", "$._links.self.href");
		mvc.perform(get(uri)).andExpect(is4xx());             // unauthorized
		mvc.perform(get(uri).auth(u)).andExpect(is4xx());     // not supported



		//////////////////////////////////////////////
		// RestfulJpaRepository
		//////////////////////////////////////////////
		mvc.perform(post("/api/bars/search")).andExpect(is4xx());         // unauthorized
		mvc.perform(post("/api/bars/search").auth(u)).andExpect(is2xx()); // ok

		mvc.perform(post(uri)).andExpect(is4xx());             // unauthorized
		mvc.perform(post(uri).auth(u)).andExpect(is2xx());     // ok




	}



}
