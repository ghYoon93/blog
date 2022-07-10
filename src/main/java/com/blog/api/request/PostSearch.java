package com.blog.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;
    public long getOffset() {
        return (long)(Math.max(1, this.page) - 1) * Math.min(this.size, MAX_SIZE);
    }
}
