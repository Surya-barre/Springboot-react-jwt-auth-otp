package in.surya.Security.controller;
import in.surya.Security.UtilJwt.JwtUtil;
import in.surya.Security.io.AuthRequest;
import in.surya.Security.io.AuthResponse;
import in.surya.Security.io.ResetPasswordRequest;
import in.surya.Security.service.AppUserDetailsService;
import in.surya.Security.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
 import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
 import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Implement your login logic here
        try{
            authenticate(request.getEmail(), request.getPassword());
         UserDetails userDetail= appUserDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken= jwtUtil.generateToken(userDetail);
        System.err.println(jwtToken);
            ResponseCookie cookie=ResponseCookie.from("jwt", jwtToken).httpOnly(true).path("/").
                    maxAge(Duration.ofDays(1)).
                    sameSite("Strict").
                    build();
            return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(new AuthResponse(request.getEmail(),jwtToken));


        }catch(BadCredentialsException ex){
            return ResponseEntity.status(401).body("Invalid email or password");
        }catch (DisabledException ex){
            return ResponseEntity.status(403).body("Account is disabled");
        } catch (Exception e){
            System.out.println("Error in login: " + e.getMessage());
            return ResponseEntity.status(500).body("An error occurred during login");
        }

    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(
 new UsernamePasswordAuthenticationToken(email, password)
        );

    }
@GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticated(@CurrentSecurityContext(expression = "authentication?.name") String email) {

        return ResponseEntity.ok(email != null);
    }
    @PostMapping("send-reset-otp")
    public void sendResetOtp(@RequestParam String email) {
        try{
            profileService.sendResetOtp(email);
        }catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while sending reset OTP"+e.getMessage());
         }



    }
    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        try{
            System.out.println("/reset-password endpoint called");
            System.out.println("Received reset password request for email: " + request.getEmail());
            System.out.println("Received OTP: " + request.getOtp());
            System.out.println("Received new password: " + request.getNewPassword());
            profileService.resetPassword(request.getEmail(),request.getOtp(),request.getNewPassword());
        }
      catch (Exception e) {
            System.out.println("Error in resetPassword: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while resetting password");
        }

    }
    @PostMapping("/send-otp")
    public void sendVerifyOtp(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        try{
            System.err.println("Received request to send verification OTP for email: " + email);
            profileService.sendOtp(email);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while sending verification OTP" + e.getMessage());
        }


    }

    @PostMapping("/verify-otp")
    public void verifyOtp(@RequestBody Map<String, Object> request,@CurrentSecurityContext(expression = "authentication?.name") String email) {
        if(request.get("otp").toString()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP is missing");
        }

        try{
            String otp= (String) request.get("otp");
            profileService.verifyOtp(email,otp);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while verifying OTP" + e.getMessage());
        }


    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)   // expire immediately
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Logged out successfully");
    }
}
