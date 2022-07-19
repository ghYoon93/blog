package com.blog.api.controller;

import com.blog.api.domain.Post;
import com.blog.api.domain.PostEditor;
import com.blog.api.request.PostCreate;
import com.blog.api.request.PostEdit;
import com.blog.api.request.PostSearch;
import com.blog.api.response.PostResponse;
import com.blog.api.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


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

        return postService.get(id);
        // 응답 클래스를 분리하자 (서비스 정책에 맞는)
    }


    // 조회 API
    // 지난 시간 = 단건 조회 API (1개의 글 Post를 가져오는 기능)
    // 이번 시간 = 여러개의 글을 조회 API
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch ) {
        return postService.getList( postSearch );
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }
}
