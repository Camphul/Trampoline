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
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Service
@AllArgsConstructor
public class BlogPostService {

    private final BlogPostRepository repository;

    public Page<BlogPost> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BlogPost createBlogPost(User user, CreateBlogPostRequest request) {
        BlogPost blogPost = new BlogPost();
        blogPost.setAuthor(user);
        blogPost.setTitle(request.getTitle());
        blogPost.setContent(request.getContent());

        return repository.save(blogPost);
    }

    public Optional<BlogPost> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public BlogPost update(BlogPost blogPost) {
        return repository.save(blogPost);
    }
}
