package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.component.TokenService;
import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.AuthDTO;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class AuthController {
    private CoachService coachService;
    private StudentService studentService;
    private TokenService tokenService;

    @Autowired
    public AuthController(CoachService coachService,
                          StudentService studentService,
                          TokenService tokenService) {
        this.coachService = coachService;
        this.studentService = studentService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authUser(final @Valid @RequestBody AuthDTO authDTO) {
        User user = getUser(authDTO.getUsername());
        JSONObject body = new JSONObject();

        if (user == null || !user.getPassword().equals(authDTO.getPassword())){
            body.put("token", JSONObject.NULL);
            body.put("message", "Email ou senha não conferem.");
            body.put("user", JSONObject.NULL);

            return new ResponseEntity<>(body.toString(), HttpStatus.FORBIDDEN);
        }

        body.put("token", tokenService.generateToken(user));
        body.put("message", "Usuario Autorizado.");

        JSONObject jsonUser = new JSONObject();
        jsonUser.put("id", user.getId());
        jsonUser.put("role", user.getUserType());
        jsonUser.put("email", user.getEmail());

        body.put("user", jsonUser);
        return new ResponseEntity<>(body.toString(), HttpStatus.ACCEPTED);
    }

    private User getUser(String username) {
        Coach coach = coachService.getByEmail(username);
        Student student = studentService.getByEmail(username);
        return coach != null ? coach : student;
    }
}
