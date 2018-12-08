package com.lucadev.example.trampoline.persistence.repository;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Entities who extend {@link com.lucadev.trampoline.data.entity.TrampolineEntity} should
 * have repositories that extend {@link TrampolineRepository}.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Repository
public interface BlogPostCommentRepository extends TrampolineRepository<BlogPostComment> {

    Page<BlogPostComment> findAllByBlogPost(BlogPost blogPost, Pageable pageable);

}
