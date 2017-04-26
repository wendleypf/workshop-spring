package br.edu.ufcg.embedded.model.DTO;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterStudent {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 20, message = "O nome deve ter entre 2 e 20 caracters.")
    private String name;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dateOfBirth;
    @Email(message = "Insira um email valido.")
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracters.")
    private String password;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 16, message = "O telefone deve ter entre 8 e 16 caracters.")
    private String phone;
    @CPF(message = "Insira um cpf valido.")
    private String cpf;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 10, max = 50, message = "O endereço deve ter entre 10 e 50 caracters.")
    private String address;

    public RegisterStudent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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
}
