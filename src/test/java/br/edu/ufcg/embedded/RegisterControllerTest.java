package br.edu.ufcg.embedded;

import br.edu.ufcg.embedded.model.DTO.RegisterCoach;
import br.edu.ufcg.embedded.service.CoachService;
import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
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
public class RegisterControllerTest {
    @Value("${local.server.port}")
    private int port;
    @Autowired
    private CoachService coachService;

    @Test
    public void testCreateCoach(){
        Gson gson = new Gson();
        RegisterCoach coach = new RegisterCoach();
        coach.setName("Wendley Paulo");
        coach.setDateOfBirth("26/09/1994");
        coach.setEmail("wendleypf@gmail.com");
        coach.setPassword("123456789");
        coach.setPhone("123456789");
        coach.setCpf("102.841.744-67");
        coach.setAddress("24 de maio 806 tambor");

        given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(coach))
                .when()
                    .port(this.port)
                    .post("/api/register")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_CREATED);
    }
}
