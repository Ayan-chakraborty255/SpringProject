package com.example.demo.controller;
 
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OtpService;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final OtpService otpService;
    private final UserRepository repo;
    private final JwtService jwtService;

    // ---------------------- SIGNUP ----------------------
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> sendSignUpOTP(@RequestBody SignupRequest request) {

        String email = request.email();
        String username = request.username();
        String rawPassword = request.password();

        User user = repo.findByEmail(email);

        // Create new user if not exists
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(rawPassword, BCrypt.gensalt()));

            user.setAccountVerified(false);
            user.setSignupOtpAttempts(0);
            repo.save(user);
        }

        // If account is already verified, stop signup
        if (user.isAccountVerified()) {
            return ResponseEntity.status(409)
                    .body(new SignupResponse(email, null, "User already registered. Please login."));
        }

        long now = System.currentTimeMillis();

        // Block if user reached request limit
        if (user.getSignupOtpBlockedUntil() != null && now < user.getSignupOtpBlockedUntil()) {
            long wait = (user.getSignupOtpBlockedUntil() - now) / 1000;
            return ResponseEntity.status(429)
                    .body(new SignupResponse(email, null, "Try after " + wait + " seconds."));
        }

        if (user.getSignupOtpAttempts() >= 3) {
            user.setSignupOtpBlockedUntil(now + (30 * 60 * 1000)); // block 30 mins
            repo.save(user);
            return ResponseEntity.status(429)
                    .body(new SignupResponse(email, null, "Too many attempts. Try after 30 minutes."));
        }

        // ✅ Generate OTP
        int otp = 100000 + new java.util.Random().nextInt(900000);
        user.setSignupOtp(otp);
        user.setSignupOtpAttempts(user.getSignupOtpAttempts() + 1);

        // ✅ Create session
        String sessionId = java.util.UUID.randomUUID().toString();
        user.setSignupSessionId(sessionId);
        repo.save(user);

        otpService.sendOtpEmail(email, String.valueOf(otp));

        return ResponseEntity.ok(new SignupResponse(email, sessionId, "OTP sent"));
    }


    // ---------------------- VERIFY OTP ----------------------
    @PostMapping("/verify")
    public ResponseEntity<String> verifySignupOtp(
            @RequestParam String email,
            @RequestParam String sessionId,
            @RequestParam int otp) {

        User user = repo.findByEmail(email);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        if (user.getSignupSessionId() == null || !user.getSignupSessionId().equals(sessionId))
            return ResponseEntity.status(400).body("Invalid session. Request OTP again.");

        if (user.getSignupOtp() != otp)
            return ResponseEntity.status(400).body("Incorrect OTP");

        // ✅ Mark verified
        user.setAccountVerified(true);
        user.setSignupOtp(0);
        user.setSignupOtpAttempts(0);
        user.setSignupSessionId(null);
        user.setSignupOtpBlockedUntil(null);
        repo.save(user);

        return ResponseEntity.ok("Signup successful! You can now login.");
    }


    // ---------------------- LOGIN ----------------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = repo.findByEmail(request.email());
        if (user == null) return ResponseEntity.status(404).body(new LoginResponse(null, "User not found"));

        if (!user.isAccountVerified())
            return ResponseEntity.status(403).body(new LoginResponse(null, "Account not verified"));

        if (!BCrypt.checkpw(request.password(), user.getPassword()))
            return ResponseEntity.status(401).body(new LoginResponse(null, "Incorrect password"));

        // ✅ Generate JWT Token
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(token, "Login success"));
    }


    // ---------------------- DTOs --------------------------------
    public record SignupRequest(String email, String username, String password) {}
    public record SignupResponse(String email, String sessionId, String message) {}

    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String message) {}
}
