package org.quickstart.kitchensink.service;

import org.quickstart.kitchensink.domain.Member;

import java.util.List;

public interface MemberService {
    List<Member> listAllMembers();

    Member lookupMemberById(long id);

    void createMember(Member member);
}
