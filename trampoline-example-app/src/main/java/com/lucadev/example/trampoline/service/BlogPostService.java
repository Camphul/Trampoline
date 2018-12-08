package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.model.CreateBlogPostRequest;
import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.repository.BlogPostRepository;
import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service to manage {@link BlogPost} entities.
 *
 * If we were to apply PreAuthorize/PostAuthorize annotations in here and we would like to generate dummy posts
 * on ContextRefreshed(startup) we must set a {@link org.springframework.security.core.context.SecurityContext}
 * to use {@link com.lucadev.trampoline.security.authentication.SystemAuthentication}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Service
@AllArgsConstructor
public class BlogPostService {

    private final BlogPostRepository repository;

    /**
     * Pageable find all
     *
     * @param pageable
     * @return
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
     * @param id the {@link UUID} of the {@link BlogPost} since we use {@link com.lucadev.trampoline.data.entity.TrampolineEntity} as base entity.
     * @return
     */
    public Optional<BlogPost> findById(UUID id) {
        return repository.findById(id);
    }

    /**
     * Delete {@link BlogPost} by id.
     * @param id
     */
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    /**
     * Update {@link BlogPost}.
     * @param blogPost
     * @return
     */
    public BlogPost update(BlogPost blogPost) {
        return repository.save(blogPost);
    }
}
