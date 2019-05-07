package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.model.dto.BlogPostCommentDto;
import com.lucadev.example.trampoline.model.dto.BlogPostDto;
import com.lucadev.example.trampoline.model.dto.BlogPostSummaryDto;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.trampoline.data.ResourceNotFoundException;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.web.model.SuccessResponse;
import com.lucadev.trampoline.web.model.UUIDDto;
import com.lucadev.trampoline.security.abac.access.prepost.PrePolicy;
import com.lucadev.trampoline.security.abac.enforcement.PolicyEnforcement;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * REST model for blog posts.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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
    @PrePolicy("BLOGPOST_LIST")
    public Page<BlogPostSummaryDto> getBlogPosts(Pageable pageable) {
        Page<BlogPost> blogPostPage = blogPostService.findAll(pageable);

        return MappedPage.of(blogPostPage, BlogPostSummaryDto::new);
    }

    /**
     * Evaluates if the principal can create blog posts by evaluating the BLOGPOST_CREATE policy.
     *
     * @param request blogpost create dto
     * @return success response including id.
     */
    @PostMapping("/blogs")
    @PrePolicy("BLOGPOST_CREATE")
    public UUIDDto createBlogPost(@RequestBody @Valid CreateBlogPostRequest request) {
        //Most likely not null since it has already authenticated.
        User user = userService.currentUserOrThrow();

        BlogPost post = blogPostService.createBlogPost(user, request);

        return new UUIDDto(post.getId());
    }

    /**
     * Evaluate BLOGPOST_VIEW with the returned BlogPost against the current principal.
     * Maybe the principal may only view his own blogposts!
     *
     * @param blogPost blogpost to view.
     * @param pageable pageable for navigating through post comments.
     * @return blogpost dto.
     */
    @GetMapping("/blogs/{blogPost}")
    //@PostAuthorize("hasPermission(returnObject,'BLOGPOST_VIEW')")
	@PrePolicy("BLOGPOST_VIEW")
    public BlogPostDto viewBlogPost(@FindById BlogPost blogPost, Pageable pageable) {
        return new BlogPostDto(blogPost,
                MappedPage.of(blogPostService.findAllComments(blogPost, pageable), BlogPostCommentDto::new));
    }

    /**
     * Might be strange not to see an annotation to authorize but some actions require some more work.
     *
     * @param blogPost Blogpost to delete.
     * @return success response.
     */
    @DeleteMapping("/blogs/{blogPost}")
    public SuccessResponse deleteBlogPost(@FindById BlogPost blogPost) {
        //Find the blog post we want to delete
        //If this fails it will throw an AccessDenied or other Exception which will stop the deleteById to be invoked.
        policyEnforcement.check(blogPost, "BLOGPOST_DELETE");

        blogPostService.deleteById(blogPost.getId());

        return new SuccessResponse();
    }

    /**
     * This is nearly the same as the {@link #deleteBlogPost(UUID)}.
     *
     * @param id blogpost id to patch.
     * @param request dto
     * @return response.
     */
    @PatchMapping("/blogs/{id}")
    public SuccessResponse patchBlogPost(@PathVariable("id") UUID id, @RequestBody @Valid CreateBlogPostRequest request) {
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

        return new SuccessResponse();
    }
}
