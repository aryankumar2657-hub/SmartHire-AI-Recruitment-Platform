package smarthire_backend.controller;

import smarthire_backend.dto.AuthResponse;
import smarthire_backend.dto.LoginRequest;
import smarthire_backend.dto.RegisterRequest;
import smarthire_backend.entity.User;
import smarthire_backend.repository.UserRepository;
import smarthire_backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

  @PostMapping("/register")
public AuthResponse register(@RequestBody RegisterRequest request) {

    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new RuntimeException("Email already in use");
    }

    User user = new User();

    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    if (request.getRole() != null) {
        user.setRole(request.getRole());
    } else {
        user.setRole("HR");
    }

    userRepository.save(user);

    String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole()
    );

    return new AuthResponse(
            token,
            user.getRole(),
            user.getName()
    );
}
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getRole(), user.getName());
    }
}