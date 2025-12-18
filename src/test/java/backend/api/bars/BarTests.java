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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.api.oauth2.Oauth2Docs;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class BarTests {


	protected Log logger = LogFactory.getLog(getClass());

	protected @Autowired MockMvc mvc;

	protected @Autowired Oauth2Docs od;	
	protected @Autowired BarDocs bd;



	@Autowired
	protected BarRepository barRepository;

	@Test 
	void contextLoads1() throws Exception{


        Jwt u = od.jose("barUser1");
		System.err.println(u);

		mvc.perform(get("/api/profile/bars")).andExpect(is2xx()).andDo(print());


		//////////////////////////////////////////////
		// CrudRepository
		//////////////////////////////////////////////
		mvc.perform(get("/api/bars")).andExpect(is4xx());         // unauthorized
		mvc.perform(get("/api/bars").auth(u)).andExpect(is4xx()); // not supported

		mvc.perform(get("/api/bars/search")).andExpect(is4xx());          // unauthorized
		mvc.perform(get("/api/bars/search").auth(u)).andExpect(is4xx());  // not supported


		mvc.perform(post("/api/bars").content(bd::newEntity)).andExpect(is4xx());
		mvc.perform(post("/api/bars").auth(u).content(bd::newEntity)).andExpect(is2xx()).andDo(print())
			.andDo(result(bd::context, "b1"));




		// String uri = bd.context("b1", "$._links.self.href");
		// mvc.perform(get(uri)).andExpect(is4xx());             // unauthorized
		// mvc.perform(get(uri).auth(u)).andExpect(is4xx());     // not supported



		// //////////////////////////////////////////////
		// // RestfulJpaRepository
		// //////////////////////////////////////////////
		// mvc.perform(post("/api/bars/search")).andExpect(is4xx());         // unauthorized
		// mvc.perform(post("/api/bars/search").auth(u)).andExpect(is2xx()); // ok

		// mvc.perform(post(uri)).andExpect(is4xx());             // unauthorized
		// mvc.perform(post(uri).auth(u)).andExpect(is2xx());     // ok




// MockHttpServletRequest:
//       HTTP Method = POST
//       Request URI = /api/bars
//        Parameters = {}
//           Headers = [Content-Type:"application/json;charset=UTF-8", Authorization:"Bearer eyJraWQiOiI1OWI0NjM1ZC03YjFlLTRlY2MtYTAwZC00Zjc5MmU1YzFiZTAiLCJhbGciOiJSUzI1NiJ9.eyJuYW1lIjoiYmFyVXNlciIsInN1YiI6ImJhclVzZXIiLCJoZWxsbyI6Impvc2UiLCJlbWFpbCI6ImJhclVzZXIifQ.oVL3grP-EO8rRYtbt2g-J70TV0jLsK3aaZDBB2DtQi_n25ehUsq1gHckb1Qi0M8zSRb9cNTkRoATJ5d3UgHw6qQe_49Kr47HfYB6-X-ZE3nU2I6xSOPCWekm4QB9nXgNDLG0vT1NhR1Df0ajTOqfcUO68OScIqWSbaIT-hlP7PkZ3UNxywrZJcON-52LhgLutuGZHJbFqz-WhEPtFMyiyiYpFSWARCgL6BBxXFdsVntt-YO4hdnrX5vBbtB4dVYvIXRwy0gBPzsVg7_THjhftliWPTAaPAkFbZSMoCmSDh4TinGm96lHLmXXw3rzou9XRx-g6t71LC_uDmcGWa_L6g"]
//              Body = {"name":"Bar-48","age":98}
//     Session Attrs = {}

// Handler:
//              Type = org.springframework.data.rest.webmvc.RepositoryEntityController
//            Method = org.springframework.data.rest.webmvc.RepositoryEntityController#postCollectionResource(RootResourceInformation, PersistentEntityResource, PersistentEntityResourceAssembler, String)

// Async:
//     Async started = false
//      Async result = null

// Resolved Exception:
//              Type = null

// ModelAndView:
//         View name = null
//              View = null
//             Model = null

// FlashMap:
//        Attributes = null

// MockHttpServletResponse:
//            Status = 201
//     Error message = null
//           Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", Location:"http://localhost/api/bars/1", Content-Type:"application/hal+json", X-Content-Type-Options:"nosniff", X-XSS-Protection:"0", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"SAMEORIGIN"]
//      Content type = application/hal+json
//              Body = {
//   "id" : 1,
//   "name" : "Bar-48",
//   "age" : 98,
//   "_links" : {
//     "self" : {
//       "href" : "http://localhost/api/bars/1"
//     },
//     "bar" : {
//       "href" : "http://localhost/api/bars/1"
//     }
//   }
// }
//     Forwarded URL = null
//    Redirected URL = http://localhost/api/bars/1
//           Cookies = []
// --------------------------------------------
	}



}
