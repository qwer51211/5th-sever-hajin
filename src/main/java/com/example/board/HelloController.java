package com.example.board;   // ← 본인 패키지 이름에 맞게 (위 파일들이랑 똑같이)

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "안녕 포스트맨";
    }
}