package org.quickstart.kitchensink.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.repository.MemberRepository;
import org.quickstart.kitchensink.service.MemberRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberRegistrationServiceImpl implements MemberRegistrationService {

    @Autowired
    private MemberRepository memberRepository;

    public void registerMember(Member member) {
        log.info("Registering {}", member.getName());
        memberRepository.save(member);

    }
}
