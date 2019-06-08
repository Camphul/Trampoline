package com.lucadev.example.trampoline.web.controller;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.example.trampoline.web.model.BlogPostCommentDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostCommentRequest;
import com.lucadev.example.trampoline.web.model.mapper.BlogPostCommentMapper;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.notify.email.EmailService;
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

import javax.mail.MessagingException;

/**
 * Controller containing endpoints to handle comments on a blogpost.
 *
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

	private final EmailService emailService;

	/**
	 * Find all comments for a given blogposts in pageable form.
	 * @param blogPost the blogpost to look for comments.
	 * @param pageable the pagination details provided by the client.
	 * @return a page containing the comments.
	 */
	@GetMapping
	public Page<BlogPostCommentDto> findAllByBlogPost(@FindById BlogPost blogPost,
			Pageable pageable) {
		return MappedPage.of(blogPostService.findAllComments(blogPost, pageable),
				mapper::toDto);
	}

	/**
	 * Submit a comment to a blogpost.
	 * @param blogPost the blogpost to add the comment to.
	 * @param request the DTO containing the comment details.
	 * @return the UUID in the form of a {@link UUIDDto} of the created comment.
	 */
	@PostMapping
	@LogUserActivity
	@PrePolicy("BLOGPOST_COMMENT_SUBMIT")
	public UUIDDto submit(@ActingUpon @FindById BlogPost blogPost,
			@RequestBody CreateBlogPostCommentRequest request) {
		User author = userService.currentUserOrThrow();
		BlogPostComment comment = mapper.fromRequest(request);
		comment = blogPostService.addComment(author, blogPost, comment);
		try {
			//Example of how you could send an awesome email notification!
			emailService.send(builder -> builder.to(blogPost.getAuthor().getEmail())
					.withSubject("New comment made on your blogpost.")
					.withAttribute("username", blogPost.getAuthor().getUsername())
					.withAttribute("poster", author.getUsername())
					.withTemplate("add_comment"));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new UUIDDto(comment.getId());
	}

	/**
	 * Get details of a specific blogpost comment.
	 * @param blogPost the blogpost.
	 * @param comment the comment inside the blogpost.
	 * @return the comment.
	 */
	@GetMapping("/{comment}")
	public BlogPostCommentDto findById(@FindById BlogPost blogPost,
			@FindById BlogPostComment comment) {
		return mapper.toDto(comment);
	}

	/**
	 * Delete a certain comment.
	 * @param blogPost the blogpost in which the comment exists.
	 * @param comment the comment to delete.
	 * @return an OK response when deleted.
	 */
	@DeleteMapping("/{comment}")
	@PrePolicy("BLOGPOST_COMMENT_DELETE")
	@LogUserActivity(value = "'Deleting comment ' + comment.id", spel = true)
	public SuccessResponse removeById(@ActingUpon @FindById BlogPost blogPost,
			@PolicyResource @FindById BlogPostComment comment) {
		blogPostService.deleteComment(blogPost, comment);
		return new SuccessResponse();
	}

}
