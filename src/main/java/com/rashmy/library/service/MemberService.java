package com.rashmy.library.service;

import com.rashmy.library.dto.MemberDTO;
import com.rashmy.library.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    List<Member> getAllMembers();
    Member findByEmail(String email);

    Page<MemberDTO> getAllMembers(Pageable pageable);
}
