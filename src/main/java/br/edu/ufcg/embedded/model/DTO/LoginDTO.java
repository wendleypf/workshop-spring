package br.edu.ufcg.embedded.model.DTO;

public class LoginDTO {
    private String token;
    private Long userId;

    public LoginDTO(String token, long userId) {
        this.token = token;
        this.userId = userId;
    }

    public LoginDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
