package com.lucadev.example.trampoline.web.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Summary DTO for BlogPost.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
public class BlogPostSummaryDto {

	private UUID id;
	private UserSummaryDto author;
	private String title;
	private Date created;

}
