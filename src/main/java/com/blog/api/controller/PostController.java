package com.blog.api.controller;

import com.blog.api.domain.Post;
import com.blog.api.request.PostCreate;
import com.blog.api.response.PostResponse;
import com.blog.api.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public void post(@RequestBody @Valid PostCreate request ) {
        // Case1. 저장한 데이터 Entity -> response 로 응답하기
        // Case2. 저장한 데이터의 primary_id -> response 로 응답하기
        //          Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신 받음
        // 가능하면 service layer 에서 repository 호출
        // Case3. 응답 필요 없음
        // Bad Case: 서버에서 -> 반드시 이렇게 할껍니다! fix
        //          -> 서버에서 차라리 유연하게 대응하는게 좋다. -> 코드를 잘 짜자
        //          -> 한 번에 일괄적으로 잘 처리되는 케이스는 없다. -> 잘 관리하는 형태가 중요
        log.info( "postCrete = {}", request );
        postService.write(request);

    }

    @GetMapping( "/posts/{postId}" )
    public PostResponse get(@PathVariable( name = "postId" ) Long id) {
        // Request 클래스
        // Response 클래스
        PostResponse response = postService.get(id);

        return response;
    }

    @GetMapping( "/posts/{postId}/rss" )
    public PostResponse getRss(@PathVariable( name = "postId" ) Long id) {

        PostResponse response = postService.get(id);
        // 응답 클래스를 분리하자 (서비스 정책에 맞는)

        return response;
    }
}
