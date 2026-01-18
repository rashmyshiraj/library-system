package com.rashmy.library.controller;

import com.rashmy.library.dto.MemberCreateDTO;
import com.rashmy.library.dto.MemberDTO;
import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Role;
import com.rashmy.library.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-member")
    public MemberDTO createMember(@RequestBody MemberCreateDTO dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        member.setRole(Role.ROLE_MEMBER);

        Member saved = memberService.saveMember(member);

        MemberDTO out = new MemberDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setEmail(saved.getEmail());
        out.setRole(saved.getRole().name());

        return out;
    }
}
