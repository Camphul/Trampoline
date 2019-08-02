package com.lucadev.example.trampoline.web.model;

import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for BlogPostComment.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
public class BlogPostCommentDto {

	private UUID id;

	private EmbeddedUser author;

	private String content;

	private Instant created;

	private Instant updated;

}
