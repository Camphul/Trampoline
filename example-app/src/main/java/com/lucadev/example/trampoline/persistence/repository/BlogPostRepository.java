package com.lucadev.example.trampoline.persistence.repository;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for blogposts.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Repository
public interface BlogPostRepository extends TrampolineRepository<BlogPost> {

}
