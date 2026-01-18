package com.rashmy.library.service;

import com.rashmy.library.entity.Member;
import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    List<Member> getAllMembers();
    Member findByEmail(String email);
}
