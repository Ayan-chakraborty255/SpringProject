package com.example.demo.controller;
 
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.OtpService;

import lombok.RequiredArgsConstructor;

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

        String email = request.email().toLowerCase();
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
    public ResponseEntity<String> verifySignupOtp(@RequestBody VerifyRequest req) {

        String email = req.email().toLowerCase();
        String sessionId = req.sessionId();
        int otp = req.otp();

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

    public record VerifyRequest(String email, String sessionId, int otp) {}
    // ---------------------- LOGIN ----------------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = repo.findByEmail(request.email());
        if (user == null) return ResponseEntity.status(404).body(new LoginResponse(null, "User not found"));

        if (!user.isAccountVerified())
            return ResponseEntity.status(403).body(new LoginResponse(null, "Account not verified"));

        if (!BCrypt.checkpw(request.password(), user.getPassword()))
            return ResponseEntity.status(401).body(new LoginResponse(null, "Incorrect password"));

        // ✅ Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // ✅ Store token in secure HTTP-only cookie
        ResponseCookie cookie = ResponseCookie.from("authToken", token)
                .httpOnly(true)
                .secure(false) 
                .path("/")    // ✅ Allow all paths
                .sameSite("Lax") // ✅ Allow sending cookie across ports
                .build();


        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(null, "Login success"));
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CookieValue(name = "authToken", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        try {
            String email = jwtService.extractEmail(token);
            return ResponseEntity.ok(email);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        
        
    } 
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)  // ✅ delete cookie
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out");
    }



   





    // ---------------------- DTOs --------------------------------
    public record SignupRequest(String email, String username, String password) {}
    public record SignupResponse(String email, String sessionId, String message) {}

    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String message) {}
}
