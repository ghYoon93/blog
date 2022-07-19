package com.blog.api.service;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.request.PostEdit;
import com.blog.api.request.PostSearch;
import com.blog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.blog.api.request.PostSearch.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.data.domain.Sort.*;
import static org.springframework.data.domain.Sort.Direction.*;

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

    // sql -> select, limit, offset
    @Test
    @DisplayName( "글 1페이지 조회" )
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj( i ->
                    Post.builder()
                            .title("제목 "  + i)
                            .content("본문 " +i)
                            .build()).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);


        // Pageable pageable = PageRequest.of(0, 5, DESC, "id");
        PostSearch postSearch = PostSearch.builder().page(1).size(10).build();
        // when
        List<PostResponse> savedPosts = postService.getList( postSearch );

        // then
        assertEquals(10L, savedPosts.size());
        assertEquals("제목 30", savedPosts.get(0).getTitle());
        assertEquals("제목 26", savedPosts.get(4).getTitle());

    }

    @Test
    @DisplayName( "글 제목 수정" )
    void test4() {
        // given
        Post post = Post.builder().
                title( "foo" )
                .content( "bar" )
                .build();
        postRepository.save( post );


        // when
        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("초가집")
                .build();

        postService.edit(post.getId(), postEdit);
        // then
        Post changed = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" + post.getId()));
        assertEquals("호돌걸", changed.getTitle());
        assertEquals("초가집", changed.getContent());

    }
}