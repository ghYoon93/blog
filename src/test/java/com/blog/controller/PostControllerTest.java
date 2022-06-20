package com.blog.controller;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World 출력")
    void test() throws Exception {
        mockMvc.perform( post( "/posts" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"title\":\"제목\", \"content\": \"본문\"}" ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "{}" ) )
                .andDo( print() );
    }

    @Test
    @DisplayName("/posts 요청 시 title 필수")
    void test2() throws Exception {
        mockMvc.perform( post( "/posts" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"title\":\"\", \"content\": \"본문\"}" ) )
                .andExpect( status().isBadRequest() )
                .andExpect(jsonPath( "$.code" ).value( "400" ))
                .andExpect(jsonPath( "$.message" ).value( "잘못된 요청입니다." ))
                .andDo( print() );
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장")
    void test3() throws Exception {

        // when
        mockMvc.perform( post( "/posts" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( "{\"title\":\"제목입니다.\", \"content\": \"본문\"}" )
                )
                .andExpect( status().isOk() )
                .andDo( print() );
        
        // then
        assertEquals(  1L, postRepository.count() );
        Post post = postRepository.findAll().get(0);
        assertEquals( post.getTitle(), "제목입니다.");
        assertEquals( post.getContent(), "본문" );
    }

}