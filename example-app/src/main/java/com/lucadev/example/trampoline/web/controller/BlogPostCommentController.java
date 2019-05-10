package com.lucadev.example.trampoline.web.controller;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.example.trampoline.web.model.BlogPostCommentDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostCommentRequest;
import com.lucadev.example.trampoline.web.model.mapper.BlogPostCommentMapper;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.logging.ActingUpon;
import com.lucadev.trampoline.security.logging.LogUserActivity;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.web.model.SuccessResponse;
import com.lucadev.trampoline.web.model.UUIDDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/10/19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs/{blogPost}/comments")
public class BlogPostCommentController {

	private final BlogPostCommentMapper mapper;
	private final BlogPostService blogPostService;
	private final UserService userService;

	@GetMapping
	public Page<BlogPostCommentDto> findAllByBlogPost(@FindById BlogPost blogPost, Pageable pageable) {
		return MappedPage.of(blogPostService.findAllComments(blogPost, pageable), mapper::toDto);
	}

	@PostMapping
	@LogUserActivity
	@PrePolicy("BLOGPOST_COMMENT_SUBMIT")
	public UUIDDto submit(@ActingUpon @FindById BlogPost blogPost, @RequestBody CreateBlogPostCommentRequest request) {
		User author = userService.currentUserOrThrow();
		BlogPostComment comment = mapper.fromRequest(request);
		comment = blogPostService.addComment(author, blogPost, comment);
		return new UUIDDto(comment.getId());
	}

	@GetMapping("/{comment}")
	public BlogPostCommentDto findById(@FindById BlogPost blogPost, @FindById BlogPostComment comment) {
		return mapper.toDto(comment);
	}

	@DeleteMapping("/{comment}")
	@PrePolicy("BLOGPOST_COMMENT_DELETE")
	@LogUserActivity(value = "'Deleting comment ' + comment.id", spel = true)
	public SuccessResponse removeById(@ActingUpon @FindById BlogPost blogPost, @PolicyResource @FindById BlogPostComment comment) {
		blogPostService.deleteComment(comment);
		return new SuccessResponse();
	}

}
