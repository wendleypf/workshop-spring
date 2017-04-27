package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.component.TokenService;
import br.edu.ufcg.embedded.model.DTO.RegisterStudent;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.model.enums.UserType;
import br.edu.ufcg.embedded.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class StudentController {
    private StudentService studentService;
    private TokenService tokenService;

    @Autowired
    public StudentController(StudentService studentService, TokenService tokenService) {
        this.studentService = studentService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getAllStudent(@RequestHeader(value = "Authorization") String token){
        User user = tokenService.getUser(token);
        if(user != null){
            return new ResponseEntity<>(studentService.getAll(),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ArrayList<Student>(), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long id){
        User user = tokenService.getUser(token);
        if(user != null){
            return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Student(), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/student", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> editStudent(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody final RegisterStudent registerStudent){
        User user = tokenService.getUser(token);
        if(user.getUserType().equals(UserType.ALUNO)){
            Student student = new Student(registerStudent.getName(),
                    registerStudent.getDateOfBirth(),
                    registerStudent.getEmail(),
                    registerStudent.getPassword(),
                    registerStudent.getPhone(),
                    registerStudent.getCpf(),
                    registerStudent.getAddress());

            student.setId(user.getId());
            return new ResponseEntity<>(studentService.update(student), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Student(), HttpStatus.UNAUTHORIZED);
    }
}
