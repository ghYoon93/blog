package com.blog.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public PostCreate() {

    }
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더의 장점
    // 1. 가독성이 좋다
    // 2. 필요한 값만 받을 수 있다.
    // 3. 객체의 불변성

}
