package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.model.dto.BlogPostCommentDto;
import com.lucadev.example.trampoline.model.dto.BlogPostDto;
import com.lucadev.example.trampoline.model.dto.BlogPostSummaryDto;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
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
 * REST controller for blog posts.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@RestController
@AllArgsConstructor
public class BlogPostController {

    private final UserService userService;
    private final BlogPostService blogPostService;
    private final PolicyEnforcement policyEnforcement;

    /**
     * Evaluate the BLOGPOST_LIST policy against the current principal.
     * Then find all blogposts and map the {@link Page} of type {@link BlogPost} to type {@link BlogPostSummaryDto}.
     * This is achieved using Trampoline's {@link MappedPage} which accepts a map function to map all contents.
     *
     * @param pageable your standard Spring pageable.
     * @return a mapped result of the blogs.
     */
    @GetMapping("/blogs")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_LIST')")
    public Page<BlogPostSummaryDto> getBlogPosts(Pageable pageable) {
        Page<BlogPost> blogPostPage = blogPostService.findAll(pageable);

        return MappedPage.of(blogPostPage, BlogPostSummaryDto::new);
    }

    /**
     * Evaluates if the principal can create blog posts by evaluating the BLOGPOST_CREATE policy.
     *
     * @param request
     * @return
     */
    @PostMapping("/blogs")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_CREATE')")
    public UUIDSuccessResponse createBlogPost(@RequestBody CreateBlogPostRequest request) {
        //Most likely not null since it has already authenticated.
        User user = userService.currentUserOrThrow();

        BlogPost post = blogPostService.createBlogPost(user, request);

        return new UUIDSuccessResponse(post.getId(), true);
    }

    /**
     * Evaluate BLOGPOST_VIEW with the returned BlogPost against the current principal.
     * Maybe the principal may only view his own blogposts!
     *
     * @param id
     * @param pageable pageable for navigating through post comments.
     * @return
     */
    @GetMapping("/blogs/{id}")
    @PostAuthorize("hasPermission(returnObject,'BLOGPOST_VIEW')")
    public BlogPostDto viewBlogPost(@PathVariable("id") UUID id, Pageable pageable) {
        BlogPost blogPost = blogPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new BlogPostDto(blogPost,
                MappedPage.of(blogPostService.findAllComments(blogPost, pageable), BlogPostCommentDto::new));
    }

    /**
     * Might be strange not to see an annotation to authorize but some actions require some more work.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/blogs/{id}")
    public SuccessResponse deleteBlogPost(@PathVariable("id") UUID id) {
        //Find the blog post we want to delete
        BlogPost blogPost = blogPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        //If this fails it will throw an AccessDenied or other Exception which will stop the deleteById to be invoked.
        policyEnforcement.check(blogPost, "BLOGPOST_DELETE");

        blogPostService.deleteById(id);

        return new SuccessResponse(true);
    }

    /**
     * This is nearly the same as the {@link #deleteBlogPost(UUID)}.
     *
     * @param id
     * @param request
     * @return
     */
    @PatchMapping("/blogs/{id}")
    public SuccessResponse patchBlogPost(@PathVariable("id") UUID id, @RequestBody CreateBlogPostRequest request) {
        BlogPost blogPost = blogPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        policyEnforcement.check(blogPost, "BLOGPOST_EDIT");

        //Only change when set in the request body
        if (request.getTitle() != null) {
            blogPost.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            blogPost.setContent(request.getContent());
        }

        blogPostService.update(blogPost);

        return new SuccessResponse(true);
    }
}
