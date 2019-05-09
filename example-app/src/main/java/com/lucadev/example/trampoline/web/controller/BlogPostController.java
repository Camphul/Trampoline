package com.lucadev.example.trampoline.web.controller;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.example.trampoline.web.model.BlogPostDto;
import com.lucadev.example.trampoline.web.model.BlogPostSummaryDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.web.model.mapper.BlogPostMapper;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.web.model.SuccessResponse;
import com.lucadev.trampoline.web.model.UUIDDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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
	public Page<BlogPostSummaryDto> findAll(Pageable pageable) {
		return MappedPage.of(blogPostService.findAll(pageable), blogPostMapper::toSummaryDto);
	}

	@PostMapping
	public UUIDDto submit(CreateBlogPostRequest request) {
		BlogPost blogPost = blogPostMapper.fromRequest(request);
		User author = userService.currentUserOrThrow();
		blogPost = blogPostService.createBlogPost(author, blogPost);
		return new UUIDDto(blogPost.getId());
	}

	@GetMapping("/{blogPost}")
	public BlogPostDto findById(@FindById BlogPost blogPost) {
		return blogPostMapper.toDto(blogPost);
	}

	@DeleteMapping("/{blogPost}")
	public SuccessResponse deleteById(@FindById BlogPost blogPost) {
		blogPostService.delete(blogPost);
		return new SuccessResponse();
	}

}
