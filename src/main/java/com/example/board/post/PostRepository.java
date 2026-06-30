package com.example.board.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String keyword);

    @Query("select p from Post p where p.content like %:keyword%")
    List<Post> searchByContent(@Param("keyword") String keyword);
}