package project2.dto;

import com.google.type.DateTime;

import java.time.LocalDateTime;

public class TokenDTO {
    private String token;
    private LocalDateTime time;

    public TokenDTO(String token, LocalDateTime time) {
        this.token = token;
        this.time = time;
    }

    public TokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
