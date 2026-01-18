package com.rashmy.library.service;

import com.rashmy.library.dto.MemberDTO;
import com.rashmy.library.entity.Member;
import com.rashmy.library.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Page<MemberDTO> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(member -> {
                    MemberDTO dto = new MemberDTO();
                    dto.setId(member.getId());
                    dto.setName(member.getName());
                    dto.setEmail(member.getEmail());
                    dto.setRole(member.getRole().name());
                    return dto;
                });
    }
}
