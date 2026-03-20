package in.surya.Security.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileRequest {

    @NotNull(message="Name cannot be null")
    private String name;
    @Email(message = "Email should be valid")
    @NotNull(message="Email cannot be null")
    private String email;
    @Size(min = 7, message = "Password should be at least 7 characters long")
    private String password;

    // 🔹 Default Constructor (Important for @RequestBody)
    public ProfileRequest() {
    }

    // 🔹 All Arguments Constructor
    public ProfileRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 🔹 Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}