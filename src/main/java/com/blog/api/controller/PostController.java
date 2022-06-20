package com.blog.api.controller;

import com.blog.api.request.PostCreate;
import com.blog.api.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request ) {

        // 가능하면 service layer 에서 repository 호출
        log.info( "postCrete = {}", request );
        postService.write( request );
        return new HashMap<>();

    }
}
