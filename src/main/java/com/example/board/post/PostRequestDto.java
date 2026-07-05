package com.example.board.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PostRequestDto {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @NotNull(message = "작성자 id는 필수입니다")
    private Long memberId;

    // getter/setter는 그대로
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}