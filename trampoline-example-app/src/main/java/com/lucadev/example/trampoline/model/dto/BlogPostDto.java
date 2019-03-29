package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link BlogPost}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostDto {

    private final UUID id;
    private final String title;
    private final String content;
    private final Date created;
    private final Date updated;
    private final UserSummaryDto author;
    private final Page<BlogPostCommentDto> comments;

    public BlogPostDto(BlogPost blogPost, Page<BlogPostCommentDto> comments) {
        this.id = blogPost.getId();
        this.title = blogPost.getTitle();
        this.content = blogPost.getContent();
        this.author = new UserSummaryDto(blogPost.getAuthor());
        this.created = blogPost.getCreated();
        this.updated = blogPost.getUpdated();
        this.comments = comments;
    }
}
