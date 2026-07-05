package com.example.board.post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 검색도 페이징 적용
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    @Query("select p from Post p where p.content like %:keyword%")
    List<Post> searchByContent(@Param("keyword") String keyword);
}