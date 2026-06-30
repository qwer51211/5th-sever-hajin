package com.example.board.member;

public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    // password 없음! 이게 DTO 쓰는 핵심 이유

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}