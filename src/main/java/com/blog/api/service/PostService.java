package com.blog.api.service;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate ) {

        // postCreate -> Entity
        Post post = new Post( postCreate.getTitle(), postCreate.getContent() );
        postRepository.save( post );
    }
}
