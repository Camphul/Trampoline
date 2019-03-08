package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.CreateBlogPostCommentRequest;
import com.lucadev.example.trampoline.model.dto.BlogPostCommentDto;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.trampoline.data.exception.ResourceNotFoundException;
import com.lucadev.trampoline.data.pagination.MappedPage;
import com.lucadev.trampoline.model.SuccessResponse;
import com.lucadev.trampoline.model.UUIDSuccessResponse;
import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller which uses trampoline-security-abac
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
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
     * @param blogId
     * @param pageable
     * @return
     */
    @GetMapping("/blogs/{blogId}/comments")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_COMMENTS_LIST')")//Check for permission before invocation
    public Page<BlogPostCommentDto> getBlogPostComments(@PathVariable("blogId") UUID blogId, Pageable pageable) {
        BlogPost blogPost = blogPostService.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(blogId));
        Page<BlogPostComment> blogPostCommentPage = blogPostService.findAllComments(blogPost, pageable);
        return MappedPage.of(blogPostCommentPage, BlogPostCommentDto::new);
    }

    /**
     * Add comment to post
     *
     * @param blogId
     * @param request
     * @return
     */
    @PostMapping("/blogs/{blogId}/comments")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_COMMENTS_CREATE')")
    public UUIDSuccessResponse getBlogPostComments(@PathVariable("blogId") UUID blogId, @RequestBody CreateBlogPostCommentRequest request) {
        BlogPost blogPost = blogPostService.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(blogId));
        User currentUser = userService.currentUserOrThrow();

        BlogPostComment comment = blogPostService.addComment(currentUser, blogPost, request);
        return new UUIDSuccessResponse(comment.getId(), true);
    }

    @GetMapping("/blogs/{blogId}/comments/{commentId}")
    @PostAuthorize("hasPermission(returnObject,'BLOGPOST_COMMENT_VIEW')")
    public BlogPostCommentDto viewBlogPostComment(@PathVariable("blogId") UUID blogId, @PathVariable("commentId") UUID commentId) {
        //BlogId is not requireed but we want to check if someone is not messing with the url
        BlogPostComment comment = blogPostService.findCommentById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
        if (!comment.getBlogPost().getId().equals(blogId)) {
            throw new ResourceNotFoundException("Could not find comment " + commentId + " for blog post " + blogId);
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

        return new SuccessResponse(true);
    }

    @PatchMapping("/blogs/{blogId}/comments/{commentId}")
    public SuccessResponse patchBlogPostComment(@PathVariable("blogId") UUID blogId, @PathVariable("commentId") UUID commentId,
                                                @RequestBody CreateBlogPostCommentRequest request) {
        BlogPostComment comment = blogPostService.findCommentById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
        if (!comment.getBlogPost().getId().equals(blogId)) {
            throw new ResourceNotFoundException("Could not find comment " + commentId + " for blog post " + blogId);
        }

        policyEnforcement.check(comment, "BLOGPOST_COMMENT_EDIT");

        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }

        blogPostService.updateComment(comment);

        return new SuccessResponse(true);
    }

}
