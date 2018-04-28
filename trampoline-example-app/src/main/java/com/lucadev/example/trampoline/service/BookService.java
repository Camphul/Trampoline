package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.persistence.entity.Book;
import com.lucadev.trampoline.security.model.User;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface BookService {

    Book create(String name);

    Book create(String name, User author);

    Book update(Book book);

    void remove(Book book);

    Book findById(UUID uuid);

    Book findByAuthor(User author);

    Book find(String name);

    List<Book> findAll();
}
