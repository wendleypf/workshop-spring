//package br.edu.ufcg.embedded.service;
//
//import br.edu.ufcg.embedded.model.DTO.AuthDTO;
//import br.edu.ufcg.embedded.model.DTO.LoginDTO;
//import br.edu.ufcg.embedded.security.TokenManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.ServletException;
//
//@Service
//public class AuthService {
//    private CoachService coachService;
//    private StudentService studentService;
//
//    @Autowired
//    public AuthService(CoachService coachService, StudentService studentService) {
//        this.coachService = coachService;
//        this.studentService = studentService;
//    }
//
//    public LoginDTO autenticar(AuthDTO user) throws ServletException {
//
//        Usuario usuarioAutenticado = this.autenticaUsuario(usuario);
//
//        String token = TokenManager.getToken( usuarioAutenticado.getEmail() );
//
//        return new LoginDTO( token, usuarioAutenticado.getId() );
//    }
//
//    private Usuario autenticaUsuario(Usuario usuario) throws ServletException{
//
//        Usuario usuarioAutenticado = usuarioService.getByEmail(usuario.getEmail());
//
//        if(usuario.getEmail() == null || usuario.getSenha() == null){
//            throw new ServletException("Nome e senha obrigatórios");
//        }
//
//        if(usuarioAutenticado == null) {
//            throw new ServletException("Usuário não encontrado.");
//        }
//
//        if (!usuarioAutenticado.getSenha().equals(usuario.getSenha())) {
//            throw new ServletException("Usuário ou senha inválida.");
//        }
//
//
//        return usuarioAutenticado;
//    }
//
//}
