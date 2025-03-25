package site.easy.to.build.crm.controller.dto;

public class LoginResponse {
    private String token;
    private String error;

    // Constructeur
    public LoginResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }

    // Getters et setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}