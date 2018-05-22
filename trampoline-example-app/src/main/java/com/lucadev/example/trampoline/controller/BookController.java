package com.lucadev.example.trampoline.controller;

import com.lucadev.example.trampoline.model.BookDto;
import com.lucadev.example.trampoline.model.CreateBookRequest;
import com.lucadev.example.trampoline.persistence.entity.Book;
import com.lucadev.example.trampoline.service.BookService;
import com.lucadev.trampoline.model.SuccessResponse;
import com.lucadev.trampoline.security.abac.policy.PolicyEnforcement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final PolicyEnforcement policyEnforcement;

    @GetMapping
    @PostFilter("hasPermission(filterObject, 'BOOKS_LIST')")
    public List<BookDto> getBookList() {
        LOGGER.info("GET book list");
        List<Book> books = bookService.findAll();
        return books.stream().map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    @PostAuthorize("hasPermission(returnObject,'BOOKS_VIEW')")
    public BookDto getBook(@PathVariable("uuid") UUID uuid) {
        LOGGER.info("GET book");
        return new BookDto(bookService.findById(uuid));
    }

    @DeleteMapping("/{uuid}")
    public SuccessResponse deleteBook(@PathVariable("uuid") UUID uuid) {
        LOGGER.info("DELETE book");
        Book book = bookService.findById(uuid);
        policyEnforcement.check(book, "BOOKS_DELETE");
        bookService.remove(book);

        return new SuccessResponse(true, "deleted book");
    }

    @PostMapping
    @PreAuthorize("hasPermission(null,'BOOKS_CREATE')")
    public BookDto postBook(@RequestBody CreateBookRequest request) {
        LOGGER.info("POST book");
        return new BookDto(bookService.create(request.getName()));
    }

    @PatchMapping("/{uuid}")
    public BookDto postBook(@PathVariable UUID uuid, @RequestBody CreateBookRequest request) {
        LOGGER.info("PATCH book");
        Book book = bookService.findById(uuid);
        policyEnforcement.check(book, "BOOKS_PATCH");
        if (request.getName() != null) {
            book.setName(request.getName());
        }
        return new BookDto(bookService.update(book));
    }
}
