package com.rashmy.library.controller;

import com.rashmy.library.dto.LoginRequest;
import com.rashmy.library.dto.LoginResponse;
import com.rashmy.library.entity.Member;
import com.rashmy.library.repository.MemberRepository;
import com.rashmy.library.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get member role from DB
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow();

        String token = jwtUtil.generateToken(member.getEmail(), member.getRole().name());

        return new LoginResponse(token);
    }
}
