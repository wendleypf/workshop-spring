package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CoachController {
    private CoachService coachService;
    private StudentService studentService;

    @Autowired
    public CoachController(CoachService coachService,
                           StudentService studentService) {
        this.coachService = coachService;
        this.studentService = studentService;
    }


}
