package com.blog.api.service;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post write(PostCreate postCreate ) {

        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        return postRepository.save( post );
    }

    public PostResponse get(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException( "존재하지 않는 글입니다." ) );

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        /**
         * PostController -> WebPostService -> Repository
         *                   PostService
         */

        return response;
    }


    /**
     * /posts -> 글 전체 조회 (검색 + 페이징)
     * /posts
     */
}
