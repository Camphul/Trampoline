package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.CreateBlogPostCommentRequest;
import com.lucadev.example.trampoline.model.dto.BlogPostCommentDto;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.ResourceNotFoundException;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.security.abac.PolicyEnforcement;
import com.lucadev.trampoline.security.abac.access.annotation.PolicyResource;
import com.lucadev.trampoline.security.abac.access.annotation.PrePolicy;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.web.model.SuccessResponse;
import com.lucadev.trampoline.web.model.UUIDDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller which uses trampoline-security-abac
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8-12-18
 */
@RestController
@AllArgsConstructor
public class BlogPostCommentController {

	private final UserService userService;
	private final BlogPostService blogPostService;
	private final PolicyEnforcement policyEnforcement;

	/**
	 * Show all comments of a post in a paginated matter.
	 *
	 * @param blogPost the blogpost
	 * @param pageable pagination
	 * @return page of blogs
	 */
	@GetMapping("/blogs/{blogPost}/comments")
	@PrePolicy("BLOGPOST_COMMENTS_LIST")//Check for permission before invocation
	public Page<BlogPostCommentDto> getBlogPostComments(@PolicyResource @FindById BlogPost blogPost, Pageable pageable) {
		Page<BlogPostComment> blogPostCommentPage = blogPostService.findAllComments(blogPost, pageable);
		return MappedPage.of(blogPostCommentPage, BlogPostCommentDto::new);
	}

	/**
	 * Add comment to post
	 *
	 * @param blogPost the blogpost
	 * @param request  dto
	 * @return success response
	 */
	@PostMapping("/blogs/{blogPost}/comments")
	@PrePolicy("BLOGPOST_COMMENTS_CREATE")
	public UUIDDto getBlogPostComments(@PolicyResource @FindById BlogPost blogPost, @RequestBody @Valid CreateBlogPostCommentRequest request) {
		User currentUser = userService.currentUserOrThrow();

		BlogPostComment comment = blogPostService.addComment(currentUser, blogPost, request);
		return new UUIDDto(comment.getId());
	}

	@GetMapping("/blogs/{blogPost}/comments/{comment}")
	@PrePolicy("BLOGPOST_COMMENT_VIEW")
	public BlogPostCommentDto viewBlogPostComment(@FindById BlogPost blogPost, @FindById BlogPostComment comment) {
		//BlogId is not requireed but we want to check if someone is not messing with the url
		if (!comment.getBlogPost().equals(blogPost)) {
			throw new ResourceNotFoundException("Could not find comment for blog post " + blogPost.getId());
		}
		return new BlogPostCommentDto(comment);
	}

	@DeleteMapping("/blogs/{blogId}/comments/{commentId}")
	public SuccessResponse deleteBlogPostComment(@PathVariable("blogId") UUID blogId, @PathVariable("commentId") UUID commentId) {
		BlogPostComment comment = blogPostService.findCommentById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
		BlogPost blogPost = blogPostService.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(blogId));
		if (!comment.getBlogPost().getId().equals(blogId)) {
			throw new ResourceNotFoundException("Could not find comment " + commentId + " for blog post " + blogId);
		}

		//We had to fetch the comment before we'd be able to check the permission.
		//This will throw an AccessDeniedException when we do not meet the permission policy
		policyEnforcement.check(comment, "BLOGPOST_COMMENT_DELETE");


		blogPostService.removeComment(blogPost, comment);

		return new SuccessResponse();
	}

	@PatchMapping("/blogs/{blogId}/comments/{commentId}")
	public SuccessResponse patchBlogPostComment(@PathVariable("blogId") UUID blogId, @PathVariable("commentId") UUID commentId,
												@RequestBody @Valid CreateBlogPostCommentRequest request) {
		BlogPostComment comment = blogPostService.findCommentById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
		if (!comment.getBlogPost().getId().equals(blogId)) {
			throw new ResourceNotFoundException("Could not find comment " + commentId + " for blog post " + blogId);
		}

		policyEnforcement.check(comment, "BLOGPOST_COMMENT_EDIT");

		if (request.getContent() != null) {
			comment.setContent(request.getContent());
		}

		blogPostService.updateComment(comment);

		return new SuccessResponse();
	}

}
