package br.edu.ufcg.embedded.model.enums;

public enum UserType {
    COACH("COACH"), ALUNO("ALUNO");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
