package in.surya.Security.io;

public class ProfileResponse {

    private String userId;
    private String name;
    private String email;
    private Boolean isAccountVerified;

    // 🔹 Default Constructor
    public ProfileResponse() {
    }

    // 🔹 All Arguments Constructor
    public ProfileResponse(String userId, String name, String email, Boolean isAccountVerified) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.isAccountVerified = isAccountVerified;
    }

    // 🔹 Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsAccountVerified() {
        return isAccountVerified;
    }

    public void setIsAccountVerified(Boolean isAccountVerified) {
        this.isAccountVerified = isAccountVerified;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}