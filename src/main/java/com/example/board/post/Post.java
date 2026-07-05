package com.example.board.post;

import com.example.board.member.Member;
import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 글 번호 (자동 증가)

    private String title;     // 제목
    private String content;   // 내용

    // 작성자 (게시글 여러 개 : 회원 한 명 = N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")   // DB에 member_id 라는 FK 컬럼이 생김
    private Member member;
    // JPA가 객체 만들 때 기본 생성자가 필요함
    public Post() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}