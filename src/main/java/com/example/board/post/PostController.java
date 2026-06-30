package com.example.board.post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")   // 이 클래스의 모든 API는 /posts 로 시작
public class PostController {

    private final PostRepository repository;

    // 스프링이 PostRepository를 자동으로 넣어줌
    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    // C: 생성 → POST /posts
    @PostMapping
    public PostResponseDto create(@Valid @RequestBody PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        Post saved = repository.save(post);
        return new PostResponseDto(saved);
    }

    // R: 전체 조회 → GET /posts
    @GetMapping
    public List<PostResponseDto> findAll() {
        return repository.findAll().stream()
                .map(PostResponseDto::new)
                .toList();
    }

    // R: 하나 조회 → GET /posts/1
    @GetMapping("/{id}")
    public PostResponseDto findOne(@PathVariable Long id) {
        Post found = repository.findById(id).orElseThrow();
        return new PostResponseDto(found);
    }

    // R: 검색 → GET /posts/search?keyword=첫
    @GetMapping("/search")
    public List<PostResponseDto> search(@RequestParam String keyword) {
        return repository.findByTitleContaining(keyword).stream()
                .map(PostResponseDto::new)
                .toList();
    }

    // U: 수정 → PUT /posts/1
    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable Long id, @Valid @RequestBody PostRequestDto dto) {
        Post found = repository.findById(id).orElseThrow();
        found.setTitle(dto.getTitle());
        found.setContent(dto.getContent());
        Post saved = repository.save(found);
        return new PostResponseDto(saved);
    }

    // D: 삭제 → DELETE /posts/1
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "삭제 완료: id=" + id;
    }
}