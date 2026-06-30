package com.example.board.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    // 회원가입
    public Member signup(MemberSignupDto dto) {
        // 이메일 중복 검사
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 가입된 이메일입니다");
        }
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());
        return repository.save(member);
    }

    // 로그인 — 이메일로 찾고 비밀번호 비교
    public Member login(String email, String password) {
        Member member = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 회원입니다"));
        if (!member.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다");
        }
        return member;
    }
}