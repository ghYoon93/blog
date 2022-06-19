package com.blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.title").value("must not be blank"))
                .andDo( print() );
    }

}