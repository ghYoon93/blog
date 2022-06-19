package com.blog.request;

import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
public class PostCreate {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
