package br.edu.ufcg.embedded.service;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.DTO.AuthDTO;
import br.edu.ufcg.embedded.model.DTO.LoginDTO;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.model.User;
import br.edu.ufcg.embedded.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
public class AuthService {
    private CoachService coachService;
    private StudentService studentService;

    @Autowired
    public AuthService(CoachService coachService, StudentService studentService) {
        this.coachService = coachService;
        this.studentService = studentService;
    }

    public LoginDTO autenticar(AuthDTO user) throws ServletException {

        User usuarioAutenticado = this.autenticaUsuario(user);

        String token = TokenManager.getToken(usuarioAutenticado.getEmail());

        return new LoginDTO( token, usuarioAutenticado.getId(), usuarioAutenticado.getUserType().getType());
    }

    private User autenticaUsuario(AuthDTO user) throws ServletException{
        Coach coach = coachService.getByEmail(user.getEmail());
        Student student = studentService.getByEmail(user.getEmail());

        User usuarioAutenticado = coach != null ? coach : student;

        if(user.getEmail() == null || user.getPassword() == null){
            throw new ServletException("Nome e senha obrigatórios");
        }

        if(usuarioAutenticado == null) {
            throw new ServletException("Usuário não encontrado.");
        }

        if (!usuarioAutenticado.getPassword().equals(user.getPassword())) {
            throw new ServletException("Usuário ou senha inválida.");
        }

        return usuarioAutenticado;
    }

}
