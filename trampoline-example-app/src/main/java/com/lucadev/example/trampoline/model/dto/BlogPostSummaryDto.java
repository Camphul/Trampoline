package com.lucadev.example.trampoline.model.dto;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * A shorter DTO than {@link BlogPostDto} which is used to display lists of blogposts.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class BlogPostSummaryDto {

	private final UUID id;
	private final UserSummaryDto author;
	private final String title;
	private final Date created;

	public BlogPostSummaryDto(BlogPost blogPost) {
		this.id = blogPost.getId();
		this.author = new UserSummaryDto(blogPost.getAuthor());
		this.title = blogPost.getTitle();
		this.created = blogPost.getCreated();
	}

}
