package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * DTO for blogpost summary to be used in lists of blogposts(since you may not want to show the comments directly)...
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostSummaryDto {

    private final UUID id;
    private final UserDto author;
    private final String title;

    public BlogPostSummaryDto(BlogPost blogPost) {
        this.id = blogPost.getId();
        this.author = new UserDto(blogPost.getAuthor());
        this.title = blogPost.getTitle();
    }

}
