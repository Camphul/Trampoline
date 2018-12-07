package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostDto {

    private final UUID id;
    private final String title;
    private final String content;
    private final UserDto author;
    private final List<BlogPostCommentDto> comments;

    public BlogPostDto(BlogPost blogPost) {
        this.id = blogPost.getId();
        this.title = blogPost.getTitle();
        this.content = blogPost.getContent();
        this.author = new UserDto(blogPost.getAuthor());
        this.comments = blogPost.getComment().stream().map(BlogPostCommentDto::new).collect(Collectors.toList());
    }
}
