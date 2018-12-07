package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostCommentDto {

    private final UUID id;
    private final UserDto author;
    private final String content;

    public BlogPostCommentDto(BlogPostComment comment) {
        this.id = comment.getId();
        this.author = new UserDto(comment.getAuthor());
        this.content = comment.getContent();
    }

}
