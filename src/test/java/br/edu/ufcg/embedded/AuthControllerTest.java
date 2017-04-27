package br.edu.ufcg.embedded;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.AuthDTO;
import br.edu.ufcg.embedded.service.CoachService;
import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;

@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=0")
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthControllerTest {
    @Value("${local.server.port}")
    private int port;
    @Autowired
    private CoachService coachService;

    @Test
    public void testAuth(){
        Gson gson = new Gson();
        AuthDTO auth = new AuthDTO();
        auth.setUsername("wendleypf@gmail.com");
        auth.setPassword("123456789");

        given().contentType(ContentType.JSON)
                    .body(gson.toJson(auth))
                .when()
                    .port(this.port)
                    .post("/api/auth")
                .then()
                    .body("message", Matchers.equalTo("Email ou senha não conferem."))
                .assertThat()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Test
    public void testAuthSuccess(){
        coachService.create(new Coach("Wendley Paulo", "26/09/1994", "wendleypf@gmail.com","123456789","123456789","102.841.744-67","24 de maio 806 tambor"));
        Gson gson = new Gson();
        AuthDTO auth = new AuthDTO();
        auth.setUsername("wendleypf@gmail.com");
        auth.setPassword("123456789");

        given().contentType(ContentType.JSON)
                    .body(gson.toJson(auth))
                .when()
                    .port(this.port)
                    .post("/api/auth")
                .then()
                    .body("message", Matchers.equalTo("Usuario Autorizado."))
                .assertThat()
                    .statusCode(HttpStatus.SC_ACCEPTED);
    }
}
