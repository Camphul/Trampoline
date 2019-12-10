package com.lucadev.example.trampoline.persistence.repository;

import com.lucadev.example.trampoline.persistence.entity.UserDocument;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Repository
public interface UserDocumentRepository extends TrampolineRepository<UserDocument> {

	Page<UserDocument> findByAuthor(User author, Pageable pageable);

}
