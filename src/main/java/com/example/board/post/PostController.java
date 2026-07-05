package com.example.board.post;

import com.example.board.member.MemberController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.board.member.Member;
import com.example.board.member.MemberRepository;

@RestController
@RequestMapping("/posts")   // 이 클래스의 모든 API는 /posts 로 시작
public class PostController {

    private final PostRepository repository;
    private final MemberRepository memberRepository;

    // 스프링이 PostRepository를 자동으로 넣어줌
    public PostController(PostRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    // C: 생성 → POST /posts
    @PostMapping
    public PostResponseDto create(@Valid @RequestBody PostRequestDto dto) {
        // 작성자를 DB에서 찾아옴 (없는 회원이면 예외)
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다"));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setMember(member);
        Post saved = repository.save(post);
        return new PostResponseDto(saved);
    }

    // R: 전체 조회 (페이징) → GET /posts?page=0&size=10&sort=id,desc
    @GetMapping
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(PostResponseDto::new);
    }

    // R: 하나 조회 → GET /posts/1
    @GetMapping("/{id}")
    public PostResponseDto findOne(@PathVariable Long id) {
        Post found = repository.findById(id).orElseThrow();
        return new PostResponseDto(found);
    }

    // R: 검색 (페이징) → GET /posts/search?keyword=첫&page=0&size=10
    @GetMapping("/search")
    public Page<PostResponseDto> search(@RequestParam String keyword, Pageable pageable) {
        return repository.findByTitleContaining(keyword, pageable)
                .map(PostResponseDto::new);
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