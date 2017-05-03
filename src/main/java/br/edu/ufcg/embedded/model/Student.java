package br.edu.ufcg.embedded.model;

import br.edu.ufcg.embedded.model.enums.UserType;

import javax.persistence.*;

@Entity(name = "Student")
@Table(name = "tb_student")
public class Student extends User {
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Coach coach;

    public Student(String name, String dateOfBirth, String email, String password, String phone, String cpf, String address, Coach coach) {
        super(name, dateOfBirth, email, password, phone, cpf, address, UserType.ALUNO);
        this.coach = coach;
    }

    public Student(String name, String dateOfBirth, String email, String password, String phone, String cpf, String address) {
        super(name, dateOfBirth, email, password, phone, cpf, address, UserType.ALUNO);
    }

    public Student() {

    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}
