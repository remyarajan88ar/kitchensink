package org.quickstart.kitchensink.controller;

import jakarta.validation.Valid;
import org.quickstart.kitchensink.domain.Member;
import org.quickstart.kitchensink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String listAllMembers(Model model) {
        model.addAttribute("allmembers", memberService.listAllMembers());
        return "index";
    }

    @GetMapping("/list/{id}")
    public String getMemberById(@PathVariable("id") Long id, Model model) {
        Member member = memberService.lookupMemberById(id);
        model.addAttribute("member", member);
        return "member";
    }

    @PostMapping("/add")
    public String registerMember(@Valid Member member, BindingResult result, Model model) {
        if (result.hasFieldErrors("name")
                || result.hasFieldErrors("email")
                || result.hasFieldErrors("age")) {

            return "register";
        }
        memberService.createMember(member);
        return "redirect:list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Member member) {
        return "register";
    }
}
