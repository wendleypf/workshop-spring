package br.edu.ufcg.embedded.config;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.service.CoachService;
import br.edu.ufcg.embedded.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    private CoachService coachService;
    private StudentService studentService;

    @Autowired
    public WebSecurityConfiguration(CoachService coachService, StudentService studentService) {
        this.coachService = coachService;
        this.studentService = studentService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Coach coach = coachService.getByEmail(username);
                Student student = studentService.getByEmail(username);

                if(coach != null){
                    return new User(coach.getEmail(), coach.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList(coach.getUserType().getType()));
                }else {
                    return new User(student.getEmail(), student.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList(student.getUserType().getType()));
                }
            }

        };
    }
}
