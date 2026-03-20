package in.surya.Security.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String verifyOtp;

    private Boolean isAccountVerified;

    private Long verifyOtpExpireAt;

    private String resetOtp;

    private Long resetOtpExpireAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // 🔹 Default Constructor (Required by JPA)
    public UserEntity() {
    }

    // 🔹 All Args Constructor
    public UserEntity(Long id, String userId, String name, String email, String password,
                      String verifyOtp, Boolean isAccountVerified,
                      Long verifyOtpExpireAt, String resetOtp,
                      Long resetOtpExpireAt, Timestamp createdAt, Timestamp updatedAt) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.verifyOtp = verifyOtp;
        this.isAccountVerified = isAccountVerified;
        this.verifyOtpExpireAt = verifyOtpExpireAt;
        this.resetOtp = resetOtp;
        this.resetOtpExpireAt = resetOtpExpireAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 🔹 Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyOtp() {
        return verifyOtp;
    }

    public void setVerifyOtp(String verifyOtp) {
        this.verifyOtp = verifyOtp;
    }

    public Boolean getIsAccountVerified() {
        return isAccountVerified;
    }

    public void setIsAccountVerified(Boolean isAccountVerified) {
        this.isAccountVerified = isAccountVerified;
    }

    public Long getVerifyOtpExpireAt() {
        return verifyOtpExpireAt;
    }

    public void setVerifyOtpExpireAt(Long verifyOtpExpireAt) {
        this.verifyOtpExpireAt = verifyOtpExpireAt;
    }

    public String getResetOtp() {
        return resetOtp;
    }

    public void setResetOtp(String resetOtp) {
        this.resetOtp = resetOtp;
    }

    public Long getResetOtpExpireAt() {
        return resetOtpExpireAt;
    }

    public void setResetOtpExpireAt(Long resetOtpExpireAt) {
        this.resetOtpExpireAt = resetOtpExpireAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}