package com.blog.api.service;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName( "글 작성" )
    void test1() {
        // given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.").build();

        // when
        postService.write(postCreate);

        // then
        Post post = postRepository.findAll().get(0);
        assertEquals(1L, postRepository.count() );
        assertEquals("제목입니다.",  post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName( "글 1개 조회" )
    void test2() {
        // given
        Post requestPost = Post.builder().title( "foo" )
                .content( "bar" )
                .build();
        postRepository.save( requestPost );


        // when
        PostResponse savedPost = postService.get( requestPost.getId() );

        // then
        assertNotNull( savedPost );
        assertEquals( "foo", savedPost.getTitle() );
        assertEquals("bar", savedPost.getContent() );

    }
}