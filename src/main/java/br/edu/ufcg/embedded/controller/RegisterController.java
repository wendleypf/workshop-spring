package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.RegisterCoach;
import br.edu.ufcg.embedded.service.CoachService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class RegisterController {
    private CoachService coachService;

    @Autowired
    public RegisterController(CoachService coachService) {
        this.coachService = coachService;
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/api/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> registerCoach(@Valid @RequestBody RegisterCoach registerCoach){
        Coach coach = new Coach(registerCoach.getName(),
                                registerCoach.getDateOfBirth(),
                                registerCoach.getEmail(),
                                registerCoach.getPassword(),
                                registerCoach.getPhone(),
                                registerCoach.getCpf(),
                                registerCoach.getAddress());

        coachService.create(coach);
        return new ResponseEntity<>("Registro realizado com sucesso.", HttpStatus.CREATED);
    }
}
