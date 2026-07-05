package com.example.board.post;

import com.example.board.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest: 웹 계층(Controller)만 띄우고, Repository는 가짜(Mock)로 대체
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;   // 가짜로 HTTP 요청을 보내는 도구

    @Autowired
    private ObjectMapper objectMapper;   // 자바 객체 <-> JSON 변환기

    @MockBean
    private PostRepository postRepository;   // 가짜 Repository

    @MockBean
    private MemberRepository memberRepository;   // PostController가 이것도 쓰므로 같이 가짜로

    @Test
    void 제목이_비어있으면_400_에러가_난다() throws Exception {
        // given: title이 빈 문자열인 잘못된 요청 JSON
        String jsonBody = """
                {
                    "title": "",
                    "content": "내용",
                    "memberId": 1
                }
                """;

        // when & then: POST /posts 요청 -> 검증 실패로 400
        mockMvc.perform(post("/posts")
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 게시글_목록을_페이징으로_조회한다() throws Exception {
        // given: 가짜 Repository가 글 1개 담긴 페이지를 리턴하도록 설정
        Post post = new Post();
        post.setTitle("제목1");
        post.setContent("내용1");
        Page<Post> fakePage = new PageImpl<>(List.of(post), PageRequest.of(0, 10), 1);
        given(postRepository.findAll(any(Pageable.class))).willReturn(fakePage);

        // when & then: GET /posts 요청 -> 200 & 응답에 제목1이 들어있는지 확인
        mockMvc.perform(get("/posts?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("제목1"));
    }
}