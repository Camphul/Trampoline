package com.lucadev.example.trampoline.service.impl;

import com.lucadev.example.trampoline.persistence.entity.Book;
import com.lucadev.example.trampoline.persistence.repository.BookRepository;
import com.lucadev.example.trampoline.service.BookService;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    @PostAuthorize("hasPermission(returnObject, 'CREATE')")
    public Book create(String name) {
        return create(name, userService.currentUser().get());
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'CREATE')")
    public Book create(String name, User author) {
        Book book = new Book(author, name);
        return bookRepository.save(book);
    }

    @Override
    @PreAuthorize("hasPermission(#book, 'PATCH')")
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @PreAuthorize("hasPermission(#book, 'DELETE')")
    public void remove(Book book) {
        bookRepository.delete(book);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Book findById(UUID uuid) {
        return bookRepository.findById(uuid).get();
    }

    @Override
    public Book findByAuthor(User author) {
        return bookRepository.findOneByAuthor(author);
    }

    @Override
    public Book find(String name) {
        return bookRepository.findOneByName(name);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
