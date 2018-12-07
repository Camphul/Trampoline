package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.model.CreateBlogPostResponse;
import com.lucadev.example.trampoline.model.dto.BlogPostDto;
import com.lucadev.example.trampoline.model.dto.BlogPostSummaryDto;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.service.BlogPostService;
import com.lucadev.trampoline.data.exception.ResourceNotFoundException;
import com.lucadev.trampoline.data.pagination.MappedPage;
import com.lucadev.trampoline.model.SuccessResponse;
import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@RestController
@AllArgsConstructor
public class BlogPostController {

    private final UserService userService;
    private final BlogPostService blogPostService;
    private final PolicyEnforcement policyEnforcement;

    @GetMapping("/blogs")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_LIST')")
    public Page<BlogPostSummaryDto> getBlogPosts(Pageable pageable) {
        Page<BlogPost> blogPostPage = blogPostService.findAll(pageable);

        return MappedPage.of(blogPostPage, pageable, BlogPostSummaryDto::new);
    }

    @PostMapping("/blogs")
    @PreAuthorize("hasPermission(null, 'BLOGPOST_CREATE')")
    public CreateBlogPostResponse createBlogPost(@RequestBody CreateBlogPostRequest request) {
        //Most likely not null since it has already authenticated.
        User user = userService.currentUserOrThrow();

        BlogPost post = blogPostService.createBlogPost(user, request);

        if (post == null) {
            return new CreateBlogPostResponse(null, false, "Failed to create blogpost");
        }
        return new CreateBlogPostResponse(post.getId(), true, "ok");
    }

    @GetMapping("/blogs/{id}")
    @PostAuthorize("hasPermission(returnObject,'BLOGPOST_VIEW')")
    public BlogPostDto viewBlogPost(@PathVariable("id") UUID id) {
        Optional<BlogPost> blogPost = blogPostService.findById(id);
        return new BlogPostDto(blogPost.orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @DeleteMapping("/blogs/{id}")
    public SuccessResponse deleteBlogPost(@PathVariable("id") UUID id) {
        Optional<BlogPost> blogPostOptional = blogPostService.findById(id);

        //This cannot be done using a authorize annotation so we use policyenforcement to check permission.
        //In this case we simply check ownership of the blogpost and check if the principal has ROLE_USER
        BlogPost blogPost = blogPostOptional.orElseThrow(() -> new ResourceNotFoundException(id));
        policyEnforcement.check(blogPost, "BLOGPOST_DELETE");

        blogPostService.deleteById(id);

        return new SuccessResponse(true, "ok");
    }

    @PatchMapping("/blogs/{id}")
    public SuccessResponse patchBlogPost(@PathVariable("id") UUID id, @RequestBody CreateBlogPostRequest request) {
        Optional<BlogPost> blogPostOptional = blogPostService.findById(id);

        //This cannot be done using a authorize annotation so we use policyenforcement to check permission.
        //In this case we simply check ownership of the blogpost and check if the principal has ROLE_USER
        BlogPost blogPost = blogPostOptional.orElseThrow(() -> new ResourceNotFoundException(id));
        policyEnforcement.check(blogPost, "BLOGPOST_EDIT");

        blogPost.setTitle(request.getTitle());
        blogPost.setContent(request.getContent());

        blogPostService.update(blogPost);

        return new SuccessResponse(true, "ok");
    }
}
