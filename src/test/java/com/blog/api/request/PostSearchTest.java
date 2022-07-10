package com.blog.api.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostSearchTest {

    @Test
    @DisplayName("Lombok Builder 기본 값")
    void test() {
        PostSearch postSearch = PostSearch.builder().page(1).build();
        System.out.println("postSearch = " + postSearch.getSize());
        assertEquals(10, postSearch.getSize());
    }



}