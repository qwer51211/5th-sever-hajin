package com.example.board.member;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입: POST /members/signup
    @PostMapping("/signup")
    public MemberResponseDto signup(@Valid @RequestBody MemberSignupDto dto) {
        Member saved = memberService.signup(dto);
        return new MemberResponseDto(saved);   // 비밀번호 빠진 응답
    }

    // 로그인: POST /members/login
    @PostMapping("/login")
    public String login(@RequestBody MemberSignupDto dto) {
        memberService.login(dto.getEmail(), dto.getPassword());
        return "로그인 성공";
    }
}