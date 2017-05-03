package br.edu.ufcg.embedded.model;

import br.edu.ufcg.embedded.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Calendar calDateOfBirth;
    @Transient
    private String dateOfBirth;
    @Column(unique = true)
    private String email;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String phone;
    @Column
    private String cpf;
    @Column
    private String address;
    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(String name, String dateOfBirth, String email, String password, String phone, String cpf, String address, UserType userType) {
        this.name = name;
        setDateOfBirth(dateOfBirth);
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cpf = cpf;
        this.address = address;
        this.userType = userType;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCalDateOfBirth() {
        return calDateOfBirth;
    }

    public void setCalDateOfBirth(Calendar calDateOfBirth) {
        this.calDateOfBirth = calDateOfBirth;
    }

    public String getDateOfBirth() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.dateOfBirth = formato.format(getCalDateOfBirth().getTime());
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();

        try {
            date.setTime(format.parse(dateOfBirth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.calDateOfBirth = date;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
