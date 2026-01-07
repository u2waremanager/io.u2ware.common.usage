package backend.api.oauth2;


import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Oauth2Tests {
    

    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
	protected @Autowired WebClient webClient;    
    protected @Autowired SecurityProperties properties;
    
	protected @Autowired Oauth2Docs od;	


	@BeforeEach
	public void setUp() {
		this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		this.webClient.getOptions().setRedirectEnabled(true);
		this.webClient.getCookieManager().clearCookies();
		// when(this.authorizationConsentService.findById(any(), any())).thenReturn(null);
	}    

    @Test
    public void context1Loads() throws Exception{

        Jwt u1 = od.jose("oauth2User");
        Jwt u2 = od.jose("u2", "ROLE_ADMIN");

        mvc.perform(get("/api/profile")).andDo(print()) .andExpect(is4xx());
        mvc.perform(get("/api/profile").auth(u1)).andDo(print()) .andExpect(is2xx());
        mvc.perform(get("/api/profile").auth(u2)).andDo(print()) .andExpect(is2xx());
    }


    @Test
    public void context2Loads() throws Exception{

        //Step 1
        mvc.perform(get("/oauth2/providers")).andDo(print()) .andDo(result(od::context, "providers")).andExpect(is2xx());
        String loginUrl = od.context("providers", "$.[0].uri");
        logger.info(loginUrl);

        //Step 2
		HtmlPage loginPage = this.webClient.getPage(loginUrl);
        assertLoginPage(loginPage);

        //Step 3
        String username = properties.getUser().getName();
        String password = properties.getUser().getPassword();
        String logonPage = signIn(loginPage, username, password).getWebResponse().getContentAsString();


        //Step 4
        DocumentContext logonContext = JsonPath.parse(logonPage);
        logger.info(logonPage);
        String token = logonContext.read("$.id_token[0]");
        logger.info(token);


        //Step 5
        mvc.perform(get("/api/profile").auth(token)).andDo(print()) .andExpect(is2xx());

    }


    ///////////////////////////////
    // 
    ///////////////////////////////
	private static void assertLoginPage(HtmlPage page) {
		assertThat(page.getUrl().toString()).endsWith("/login");

		HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
		HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
		HtmlButton signInButton = page.querySelector("button");

		assertThat(usernameInput).isNotNull();
		assertThat(passwordInput).isNotNull();
		assertThat(signInButton.getTextContent()).isEqualTo("Sign in");
	}

	private static <P extends Page> P signIn(HtmlPage page, String username, String password) throws IOException {
		HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
		HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
		HtmlButton signInButton = page.querySelector("button");

		usernameInput.type(username);
		passwordInput.type(password);
		return signInButton.click();
	}

}
