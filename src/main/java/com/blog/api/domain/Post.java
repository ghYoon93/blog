package com.blog.api.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor( access = AccessLevel.PUBLIC )
@Entity
public class Post {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder().title(title).content(content);
    }

    public void edit(PostEditor postEdit) {
        title = postEdit.getTitle();
        content = postEdit.getContent();
    }

    /* 서비스의 정책을 넣지마세요!!! 절대!!!
    public String getTitle() {

        return this.title.substring(0, 10);
    }

     */
}
