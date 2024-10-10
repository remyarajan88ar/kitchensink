package org.quickstart.kitchensink.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.repository.MemberRepository;

public class MemberRegistrationServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberRegistrationServiceImpl registrationService = new MemberRegistrationServiceImpl();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerMemberTest() {
        Member member = new Member(1L, "sds", "email", "2345");
        registrationService.registerMember(member);
        Mockito.verify(memberRepository, Mockito.times(1)).save(member);
    }
}
