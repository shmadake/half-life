package org.codevil.halflife.service;

import lombok.RequiredArgsConstructor;
import org.codevil.halflife.dto.AuthResponse;
import org.codevil.halflife.dto.LoginRequest;
import org.codevil.halflife.dto.RegisterRequest;
import org.codevil.halflife.entity.User;
import org.codevil.halflife.repository.UserRepository;
import org.codevil.halflife.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName());

        return new AuthResponse(user.getId(), token, user.getName());
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName());

        return new AuthResponse(user.getId(), token, user.getName());
    }
}