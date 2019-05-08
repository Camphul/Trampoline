package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.model.CreateBlogPostCommentRequest;
import com.lucadev.example.trampoline.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.persistence.repository.BlogPostCommentRepository;
import com.lucadev.example.trampoline.persistence.repository.BlogPostRepository;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service to manage {@link BlogPost} entities.
 * <p>
 * If we were to apply PreAuthorize/PostAuthorize annotations in here and we would like to generate dummy posts
 * on ContextRefreshed(startup) we must set a {@link org.springframework.security.core.context.SecurityContext}
 * to use {@link com.lucadev.trampoline.security.authentication.SystemAuthentication}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Service
@AllArgsConstructor
public class BlogPostService {

	private final BlogPostRepository repository;
	private final BlogPostCommentRepository commentRepository;

	/**
	 * Pageable find all
	 *
	 * @param pageable pagination.
	 * @return page of blogs.
	 */
	public Page<BlogPost> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	/**
	 * Create a new blog post
	 *
	 * @param user    author of the blogpost
	 * @param request the request containing our blogpost information.
	 * @return the persisted {@link BlogPost}
	 */
	public BlogPost createBlogPost(User user, CreateBlogPostRequest request) {
		BlogPost blogPost = new BlogPost();
		blogPost.setAuthor(user);
		blogPost.setTitle(request.getTitle());
		blogPost.setContent(request.getContent());

		return repository.save(blogPost);
	}

	/**
	 * Find {@link BlogPost} by id.
	 *
	 * @param id the {@link UUID} of the {@link BlogPost} since we use {@link com.lucadev.trampoline.data.entity.TrampolineEntity} as base entity.
	 * @return resolved blogpost.
	 */
	public Optional<BlogPost> findById(UUID id) {
		return repository.findById(id);
	}

	/**
	 * Delete {@link BlogPost} by id.
	 *
	 * @param id blogpost id to delete.
	 */
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}

	/**
	 * Update {@link BlogPost}.
	 *
	 * @param blogPost the blogpost to update.
	 * @return updated blogpost.
	 */
	public BlogPost update(BlogPost blogPost) {
		return repository.save(blogPost);
	}

	/**
	 * Add a comment
	 *
	 * @param author   the author of the comment.
	 * @param blogPost the blogpost to put it on.
	 * @param request  the comment dto.
	 * @return the created comment.
	 */
	@Transactional
	public BlogPostComment addComment(User author, BlogPost blogPost, CreateBlogPostCommentRequest request) {
		BlogPostComment comment = new BlogPostComment(author, request.getContent());
		comment = commentRepository.save(comment);

		comment.setBlogPost(blogPost);
		blogPost.getComments().add(comment);
		repository.save(blogPost);
		return comment;
	}

	/**
	 * Find pageable comments
	 *
	 * @param blogPost blogpost to find comments on.
	 * @param pageable pagination.
	 * @return page of blog comments.
	 */
	public Page<BlogPostComment> findAllComments(BlogPost blogPost, Pageable pageable) {
		return commentRepository.findAllByBlogPost(blogPost, pageable);
	}

	/**
	 * Find comment by id
	 *
	 * @param commentId comment id.
	 * @return resolved comment.
	 */
	public Optional<BlogPostComment> findCommentById(UUID commentId) {
		return commentRepository.findById(commentId);
	}

	/**
	 * Delete comment
	 *
	 * @param commentId comment id to delete.
	 */
	public void deleteCommentById(UUID commentId) {
		commentRepository.deleteById(commentId);
	}

	/**
	 * Update comment
	 *
	 * @param comment comment to update.
	 */
	public void updateComment(BlogPostComment comment) {
		commentRepository.save(comment);
	}

	/**
	 * Removes a comment
	 *
	 * @param blogPost blogpost
	 * @param comment  comment to remove from blogpost.
	 */
	@Transactional
	public void removeComment(BlogPost blogPost, BlogPostComment comment) {
		//Unlink relations
		comment.setBlogPost(null);
		blogPost.getComments().remove(comment);
		//Save blogpost
		update(blogPost);
		//Now it's save to delete
		commentRepository.delete(comment);
	}
}
