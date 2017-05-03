package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.component.TokenService;
import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.RegisterCoach;
import br.edu.ufcg.embedded.model.DTO.RegisterStudent;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.model.enums.UserType;
import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class CoachController {
    private CoachService coachService;
    private StudentService studentService;
    private TokenService tokenService;

    @Autowired
    public CoachController(CoachService coachService,
                           StudentService studentService,
                           TokenService tokenService) {
        this.coachService = coachService;
        this.studentService = studentService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/coach", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllCoach(@RequestHeader(value = "Authorization") String token) {
        User user = tokenService.getUser(token);
        if(user != null){
            return new ResponseEntity<>(coachService.getAll(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ArrayList<Coach>(), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/coach/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> getCoachById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long id){
        User user = tokenService.getUser(token);
        if(user != null){
            return new ResponseEntity<>(coachService.getById(id), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Coach(), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/coach", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coach> editCoach(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody final RegisterCoach registerCoach){
        User user = tokenService.getUser(token);
        if (user.getUserType().equals(UserType.COACH)){
            Coach coach = new Coach(registerCoach.getName(),
                    registerCoach.getDateOfBirth(),
                    registerCoach.getEmail(),
                    registerCoach.getPassword(),
                    registerCoach.getPhone(),
                    registerCoach.getCpf(),
                    registerCoach.getAddress());

            coach.setId(user.getId());
            return new ResponseEntity<>(coachService.update(coach), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Coach(), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/coach/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> registerStudent(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody final RegisterStudent registerStudent){
        User user = tokenService.getUser(token);
        if(user.getUserType().equals(UserType.COACH)){
            Coach coach = coachService.getById(user.getId());
            Student student = new Student(registerStudent.getName(),
                    registerStudent.getDateOfBirth(),
                    registerStudent.getEmail(),
                    registerStudent.getPassword(),
                    registerStudent.getPhone(),
                    registerStudent.getCpf(),
                    registerStudent.getAddress(),coach);

            studentService.create(student);
            return new ResponseEntity<String>("Aluno criado com sucesso.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Acesso não autorizado.", HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/coach/student/{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteStudent(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long id){
        User user = tokenService.getUser(token);
        if(user.getUserType().equals(UserType.COACH)) {
            studentService.removeById(id);
            return new ResponseEntity<>("Aluno deletado com sucesso.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Acesso não autorizado.", HttpStatus.UNAUTHORIZED);
    }

}
