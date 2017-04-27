package br.edu.ufcg.embedded;

import br.edu.ufcg.embedded.service.CoachService;
import com.jayway.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=0")
@RunWith(SpringJUnit4ClassRunner.class)
public class CoachControllerTest {
    @Value("${local.server.port}")
    private int port;
    @Autowired
    private CoachService coachService;

    @Test
    public void testInsertStudent(){
    }
}
