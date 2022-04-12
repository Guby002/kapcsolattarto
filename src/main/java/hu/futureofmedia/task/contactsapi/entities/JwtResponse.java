package hu.futureofmedia.task.contactsapi.entities;

import java.util.List;

public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Basic";
    private String username;
    private String password;
    private List roles;
    public JwtResponse(String token, Long id, String username, String password, List roles) {
        this.id = id;
        this.token =token;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}