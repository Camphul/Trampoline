package com.lucadev.example.trampoline.persistence.repository;

import com.lucadev.example.trampoline.persistence.entity.Book;
import com.lucadev.trampoline.data.repository.TrampolineRepository;
import com.lucadev.trampoline.security.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Repository
public interface BookRepository extends TrampolineRepository<Book> {

    Book findOneByName(String name);

    Book findOneByAuthor(User author);

}
