package com.rashmy.library.controller;

import com.rashmy.library.dto.MemberCreateDTO;
import com.rashmy.library.dto.MemberDTO;
import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Role;
import com.rashmy.library.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberCreateDTO dto) {
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

        return ResponseEntity.ok(out);
    }

    @GetMapping
    public ResponseEntity<Page<MemberDTO>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }
}
