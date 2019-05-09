package com.lucadev.example.trampoline.web.model;

import lombok.Data;

import java.util.Date;
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
	private UserSummaryDto author;
	private String content;
	private Date created;
	private Date updated;

}
