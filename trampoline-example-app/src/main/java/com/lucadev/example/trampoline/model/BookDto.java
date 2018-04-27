package com.lucadev.example.trampoline.model;

import com.lucadev.example.trampoline.persistence.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {

    private UUID id;
    private String name;
    private UUID author;
    private String authorName;

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor().getId();
        this.authorName = book.getAuthor().getUsername();
    }

}
