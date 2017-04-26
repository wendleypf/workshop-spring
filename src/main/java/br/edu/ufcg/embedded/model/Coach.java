package br.edu.ufcg.embedded.model;

import br.edu.ufcg.embedded.model.enums.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Coach")
@Table(name = "tb_coach")
public class Coach extends User {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> studentList;

    public Coach(String name, String dateOfBirth, String email,String password, String phone, String cpf, String address) {
        super(name, dateOfBirth, email, password, phone, cpf, address, UserType.COACH);
        this.studentList = new ArrayList<>();
    }

    public Coach() {

    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void setStudent(Student student) {
        if(student != null){
            studentList.add(student);
        }
    }

    public void removeStudent(Student student){
        if (student != null) {
            studentList.remove(student);
        }
    }
}
