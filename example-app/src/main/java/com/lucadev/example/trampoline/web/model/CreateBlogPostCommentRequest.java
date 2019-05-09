package com.lucadev.example.trampoline.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model used when creating a new comment.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
public class CreateBlogPostCommentRequest {

	@NotBlank
	@NotNull
	@Size(min = 2, max = 1024)
	private String content;

}
