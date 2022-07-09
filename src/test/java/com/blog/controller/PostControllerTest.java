package com.blog.controller;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World 출력")
    void test() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString( request );

        mockMvc.perform( post( "/posts" )
                        .contentType( APPLICATION_JSON )
                        .content( json )
                )
                .andExpect( status().isOk() )
                .andExpect( content().string( "" ) )
                .andDo( print() );
    }

    @Test
    @DisplayName("/posts 요청 시 title 필수")
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform( post( "/posts" )
                        .contentType( APPLICATION_JSON )
                        .content(json) )
                .andExpect( status().isBadRequest() )
                .andExpect(jsonPath( "$.code" ).value( "400" ))
                .andExpect(jsonPath( "$.message" ).value( "잘못된 요청입니다." ))
                .andDo( print() );
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장")
    void test3() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform( post( "/posts" )
                        .contentType( APPLICATION_JSON )
                        .content( json )
                )
                .andExpect( status().isOk() )
                .andDo( print() );
        
        // then
        assertEquals(  1L, postRepository.count() );
        Post post = postRepository.findAll().get(0);
        assertEquals( post.getTitle(), "제목입니다.");
        assertEquals( post.getContent(), "내용입니다." );
    }

    @Test
    @DisplayName( "글 1개 조회" )
    void test4() throws Exception {
        // given
        Post post = Post.builder().title("123456789012345")
                .content("bar")
                .build();

        postRepository.save( post );
        // 클라이언트 요구사항
            // json 응답에서 title 값 길이를 최대 10글자로 해주세요.
            // 이런 처리는 클라이언트에서 하는 것이 좋다.


        // expected
        mockMvc.perform( get( "/posts/{postId}", post.getId() )
                        .contentType( APPLICATION_JSON )
                )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" ).value( post.getId() ) )
                .andExpect( jsonPath( "$.title" ).value( "1234567890" ) )
                .andExpect( jsonPath(  "$.content" ).value( post.getContent() ) )
                .andDo( print() );
    }

    @Test
    @DisplayName( "글 여러개 조회" )
    void test5() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj( i ->
                        Post.builder()
                                .title("제목 "  + i)
                                .content("본문 " +i)
                                .build()).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);
        // 클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.
        // 이런 처리는 클라이언트에서 하는 것이 좋다.


        // expected
        mockMvc.perform( get( "/posts?page=1&size=5&sort=id,desc" )
                        .contentType( APPLICATION_JSON )
                )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("제목 30"))
                .andExpect(jsonPath("$[0].content").value("본문 30"))
                .andDo( print() );
    }

}