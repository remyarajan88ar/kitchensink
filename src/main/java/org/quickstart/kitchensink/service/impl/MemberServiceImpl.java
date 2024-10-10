package org.quickstart.kitchensink.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.exception.MemberNotFoundException;
import org.quickstart.kitchensink.repository.MemberRepository;
import org.quickstart.kitchensink.service.MemberRegistrationService;
import org.quickstart.kitchensink.service.MemberService;
import org.quickstart.kitchensink.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private MemberRegistrationService memberRegistrationService;

    @Override
    public List<Member> listAllMembers() {
        return memberRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Member lookupMemberById(long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElseThrow(() -> new MemberNotFoundException("Member not found"));
    }

    @Override
    public void createMember(Member member) {
        validationUtil.validateMember(member);
        memberRegistrationService.registerMember(member);
    }
}
