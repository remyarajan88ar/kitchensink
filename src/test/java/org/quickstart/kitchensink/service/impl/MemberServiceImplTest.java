package org.quickstart.kitchensink.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.exception.MemberNotFoundException;
import org.quickstart.kitchensink.repository.MemberRepository;
import org.quickstart.kitchensink.service.MemberRegistrationService;
import org.quickstart.kitchensink.util.ValidationUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private MemberRegistrationService memberRegistrationService;

    @InjectMocks
    private MemberServiceImpl memberService = new MemberServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAllMembersTest() {
        Member member = new Member(1L, "John", "John@gmail.com", "1234567890");
        Member member1 = new Member(2L, "Tom", "Tom@gmail.com", "3456789013");
        List<Member> members = Arrays.asList(member, member1);
        Mockito.when(memberRepository.findAllByOrderByNameAsc()).thenReturn(members);
        List<Member> memberList = memberService.listAllMembers();
        Assertions.assertEquals(2, memberList.size());
    }

    @Test
    void lookupMemberByIdTest() {
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Member(1L, "abc", "email", "1234")));
        Member member = memberService.lookupMemberById(1);
        Assertions.assertEquals(1, member.getId());
    }

    @Test
    void lookupMemberByIdNotFoundTest() {
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(MemberNotFoundException.class, () -> memberService.lookupMemberById(1), "Member not found");
    }

    @Test
    void createMemberTest() {
        memberService.createMember(new Member(1L, "abc", "email", "1234"));
        Mockito.verify(validationUtil, Mockito.times(1)).validateMember(Mockito.any());
        Mockito.verify(memberRegistrationService, Mockito.times(1)).registerMember(Mockito.any());
    }
}
