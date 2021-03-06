package com.lucadev.example.trampoline.web.controller;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.example.trampoline.web.model.BlogPostDto;
import com.lucadev.example.trampoline.web.model.BlogPostSummaryDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.web.model.mapper.BlogPostMapper;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.logging.ActingUpon;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.annotation.IgnoreSecurity;
import com.lucadev.trampoline.web.model.SuccessResponse;
import com.lucadev.trampoline.web.model.UUIDDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Blogpost controller.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogPostController {

	private final BlogPostService blogPostService;

	private final BlogPostMapper blogPostMapper;

	private final UserService userService;

	/**
	 * Get all blogposts.
	 * @param pageable pagination details.
	 * @return page with blog summaries.
	 */
	@GetMapping
	@IgnoreSecurity
	public Page<BlogPostSummaryDto> findAll(Pageable pageable) {
		return MappedPage.of(this.blogPostService.findAll(pageable),
				this.blogPostMapper::toSummaryDto);
	}

	@PostMapping
	@LogUserActivity
	@PrePolicy("BLOGPOST_SUBMIT")
	public UUIDDto submit(@Valid @RequestBody CreateBlogPostRequest request) {
		BlogPost blogPost = this.blogPostMapper.fromRequest(request);
		User author = this.userService.currentUserOrThrow();
		blogPost = this.blogPostService.createBlogPost(author, blogPost);
		return new UUIDDto(blogPost.getId());
	}

	@GetMapping("/{blogPost}")
	public BlogPostDto findById(@FindById BlogPost blogPost) {
		return this.blogPostMapper.toDto(blogPost);
	}

	@LogUserActivity
	@DeleteMapping("/{blogPost}")
	@PrePolicy("BLOGPOST_DELETE")
	public SuccessResponse deleteById(
			@ActingUpon @PolicyResource @FindById BlogPost blogPost) {
		this.blogPostService.delete(blogPost);
		return new SuccessResponse();
	}

}
