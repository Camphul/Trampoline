package com.lucadev.example.trampoline.persistence.repository;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repository for comments.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Repository
public interface BlogPostCommentRepository extends TrampolineRepository<BlogPostComment> {

	Page<BlogPostComment> findAllByBlogPost(BlogPost blogPost, Pageable pageable);

}
