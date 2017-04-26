package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.component.TokenService;
import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.AuthDTO;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/api/auth", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> authUser(final @Valid @RequestBody AuthDTO authDTO) {
        User user = getUser(authDTO.getUsername());
        JSONObject body = new JSONObject();

        if (user == null || !user.getPassword().equals(authDTO.getPassword())){
            body.put("token", JSONObject.NULL);
            body.put("message", "Email ou senha não conferem.");
            body.put("usuario", JSONObject.NULL);

            return new ResponseEntity<>(body.toString(), HttpStatus.FORBIDDEN);
        }

        body.put("token", tokenService.generateToken(user));
        body.put("message", "Usuario Autorizado.");

        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("id", user.getId());
        jsonUsuario.put("name", user.getUserType());
        jsonUsuario.put("email", user.getEmail());

        body.put("user", jsonUsuario);
        return new ResponseEntity<>(body.toString(), HttpStatus.ACCEPTED);
    }

    private User getUser(String username) {
        Coach coach = coachService.getByEmail(username);
        Student student = studentService.getByEmail(username);
        return coach != null ? coach : student;
    }
}
