package com.example.board.post;

public class PostResponseDto {
    private Long id;
    private String title;
    private String content;

    // Post 엔티티를 받아서 DTO로 변환하는 생성자
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}