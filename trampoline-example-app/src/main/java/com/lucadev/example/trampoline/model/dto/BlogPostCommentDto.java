package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link BlogPostComment}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostCommentDto {

    private final UUID id;
    private final UserSummaryDto author;
    private final String content;
    private final Date created;
    private final Date updated;

    public BlogPostCommentDto(BlogPostComment comment) {
        this.id = comment.getId();
        this.author = new UserSummaryDto(comment.getAuthor());
        this.content = comment.getContent();
        this.created = comment.getCreated();
        this.updated = comment.getUpdated();
    }

}
