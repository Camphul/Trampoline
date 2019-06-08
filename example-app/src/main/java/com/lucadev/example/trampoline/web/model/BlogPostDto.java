package com.lucadev.example.trampoline.web.model;

import com.lucadev.trampoline.security.web.model.EmbeddedUser;
import lombok.Data;

import java.util.Date;
import java.util.List;
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

	private Date created;

	private Date updated;

	private EmbeddedUser author;

	private List<BlogPostCommentDto> comments;

}
