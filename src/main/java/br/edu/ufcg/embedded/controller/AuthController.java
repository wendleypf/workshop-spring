package br.edu.ufcg.embedded.controller;

import br.edu.ufcg.embedded.model.DTO.AuthDTO;
import br.edu.ufcg.embedded.model.DTO.LoginDTO;
import br.edu.ufcg.embedded.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
@CrossOrigin
public class AuthController {
    public static final String CHAVE_CRIPTOGRAFIA = "chaveAleatoria";
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<LoginDTO> autenticar(@RequestBody AuthDTO user){
        try{
            LoginDTO response = authService.autenticar(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ServletException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
