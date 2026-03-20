package in.surya.Security.controller;

import in.surya.Security.io.ProfileRequest;
import in.surya.Security.io.ProfileResponse;
import in.surya.Security.service.EmailService;
import in.surya.Security.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
@Autowired
private EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        System.out.println("This is register method in ProfileController");
ProfileResponse response=profileService.createProfile(request);
        System.out.println("request " + request);
          emailService.sendWelcomeEmail(response.getEmail(),response.getName());
        return response;
    }

//    @GetMapping("/getting")
//    public String surya(){
//        System.out.println("This surya virat kohlikkk");
//        return "our authentication is success";
//    }

    @GetMapping("/profile2")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        System.out.println("This is getProfile method in ProfileController");
        return profileService.getProfile(email);
    }
}