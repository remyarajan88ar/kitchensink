package org.quickstart.kitchensink.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/members")
@Slf4j
public class MemberRestController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public List<Member> listAllMembers() {
        return memberService.listAllMembers();
    }

    @GetMapping("/{id:[0-9][0-9]*}")
    public Member lookupByMemberId(@PathVariable("id") long id) {
        return memberService.lookupMemberById(id);

    }

    @PostMapping("")
    public void createMember(@Valid Member member, BindingResult result, Model model) {
        memberService.createMember(member);
    }


}
