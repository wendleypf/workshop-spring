package br.edu.ufcg.embedded.model;

import br.edu.ufcg.embedded.model.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Coach")
@Table(name = "tb_coach")
public class Coach extends User {

    public Coach(String name, String dateOfBirth, String email,String password, String phone, String cpf, String address) {
        super(name, dateOfBirth, email, password, phone, cpf, address, UserType.COACH);
    }

    public Coach() {

    }

}
