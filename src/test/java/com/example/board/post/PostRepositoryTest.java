package com.example.board.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest: JPA 관련(Repository)만 가볍게 띄워서 테스트
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시글을_저장하면_id가_생긴다() {
        // given: 저장할 글을 준비
        Post post = new Post();
        post.setTitle("스프링 공부");
        post.setContent("JPA 배우는 중");

        // when: 실제로 저장
        Post saved = postRepository.save(post);

        // then: id가 자동으로 생겼는지 확인
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 공부");
    }

    @Test
    void 제목으로_검색하면_해당_글만_나온다() {
        // given: 서로 다른 제목의 글 2개 저장
        Post post1 = new Post();
        post1.setTitle("스프링 부트 시작하기");
        post1.setContent("내용1");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("자바 기초");
        post2.setContent("내용2");
        postRepository.save(post2);

        // when: "스프링"으로 검색
        Page<Post> result = postRepository.findByTitleContaining("스프링", PageRequest.of(0, 10));

        // then: "스프링" 들어간 글 1개만 나와야 함
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("스프링 부트 시작하기");
    }
}