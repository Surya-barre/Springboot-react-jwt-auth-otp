package in.surya.Security.service;

import in.surya.Security.entity.UserEntity;
import in.surya.Security.io.ProfileRequest;
import in.surya.Security.io.ProfileResponse;
import in.surya.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        UserEntity newProfile = convertToUserEntity(request);
        if(!userRepository.existsByEmail(request.getEmail())){
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }


        throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");

    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return convertToProfileResponse(user);
    }

    @Override
    public void sendResetOtp(String email) {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            // Generate OTP and set it in the user entity
        String opt=String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        //calculate expiry time for OTP (e.g., 15 minutes from now)
        long otpExpireAt=System.currentTimeMillis()+15*60*1000;
   //update user entity with OTP and expiry time

        user.setResetOtp(opt);
        user.setResetOtpExpireAt(otpExpireAt);
        userRepository.save(user);

        try
        {
            //send the reset otp email
            emailService.sendResetOtpEmail(user.getEmail(),opt);
        }catch (Exception e){
            System.out.println("Error sending reset OTP email: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send reset OTP email");
        }

    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getResetOtp() == null || !user.getResetOtp().equals(otp) || user.getResetOtpExpireAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }
        System.out.println(otp);

        // Update the user's password and clear the OTP fields
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetOtp(null);
        user.setResetOtpExpireAt(0L);
        userRepository.save(user);
        System.out.println("successfully reset password for user: " + email);
    }
//send verification otp to user email for account verification and set otp and expiry time in user entity
    @Override
    public void sendOtp(String email) {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
         if (user.getIsAccountVerified()!=null && user.getIsAccountVerified()) {
             System.out.println("User account is already verified for email: " + email);
          return;
          }
         // Generate 6 digit OTP and set it in the user entity
        String otp=String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        //calculate expiry time for OTP (e.g., 24 hours from now)
        long otpExpireAt=System.currentTimeMillis()+24*60*60*1000;
        //update user entity with OTP and expiry time
        user.setVerifyOtp(otp);
        user.setVerifyOtpExpireAt(otpExpireAt);
        userRepository.save(user);
        System.err.println("For the verification otp email");
        try{
            emailService.sendVerificationOtpEmail(user.getEmail(),otp);
        }catch (Exception e) {
            System.err.println("Error sending verification OTP email: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send verification OTP email ");

        }
    }
//reset password verification and expiry time verification for otp
    @Override
    public void verifyOtp(String email, String otp) {
          userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserEntity user=userRepository.findByEmail(email).get();
        if(user.getVerifyOtp()==null || !user.getVerifyOtp().equals(otp)
        || user.getVerifyOtpExpireAt()<System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }
        user.setIsAccountVerified(true);
        user.setVerifyOtp(null);
        user.setVerifyOtpExpireAt(0L);
        userRepository.save(user);

    }



    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {

        ProfileResponse response = new ProfileResponse();

        response.setUserId(newProfile.getUserId());
        response.setName(newProfile.getName());
        response.setEmail(newProfile.getEmail());
        response.setIsAccountVerified(newProfile.getIsAccountVerified());

        return response;
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {

        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsAccountVerified(false);
        user.setResetOtpExpireAt(0L);
        user.setVerifyOtp(null);
        user.setVerifyOtpExpireAt(0L);
        user.setResetOtp(null);

        return user;
    }


}