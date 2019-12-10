package com.lucadev.example.trampoline.web.model;

import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for BlogPost.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
public class BlogPostDto {

	private UUID id;

	private String title;

	private String content;

	private Instant created;

	private Instant updated;

	private EmbeddedUser author;

	private Set<BlogPostCommentDto> comments;

}
