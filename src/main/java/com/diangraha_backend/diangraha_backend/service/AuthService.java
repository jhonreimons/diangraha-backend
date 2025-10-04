package com.diangraha_backend.diangraha_backend.service;


import com.diangraha_backend.diangraha_backend.entity.User;
import com.diangraha_backend.diangraha_backend.repository.UserRepository;
import com.diangraha_backend.diangraha_backend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public void register(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        String hashed = encoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashed);
        userRepo.save(user);
    }

    // LOGIN
    public String login(String username, String password) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(username);
    }
}
