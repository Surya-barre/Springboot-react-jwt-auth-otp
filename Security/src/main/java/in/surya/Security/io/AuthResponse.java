package in.surya.Security.io;

public class AuthResponse {

    private String email;
    private String token;

    public AuthResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {   // ⭐ THIS IS THE MISSING METHOD
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}