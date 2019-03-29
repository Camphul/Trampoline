package com.lucadev.example.trampoline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@ToString
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CreateBlogPostRequest {

	@NotBlank
	@NotNull
	@Size(min=2, max=32)
    private String title;
	@NotBlank
	@NotNull
	@Size(min=2, max=1024)
    private String content;

}
