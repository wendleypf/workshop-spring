package br.edu.ufcg.embedded.model;

import br.edu.ufcg.embedded.model.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Student")
@Table(name = "tb_student")
public class Student extends User {
    public Student(String name, String dateOfBirth, String email, String password, String phone, String cpf, String address) {
        super(name, dateOfBirth, email, password, phone, cpf, address, UserType.ALUNO);
    }

    public Student() {

    }
}
