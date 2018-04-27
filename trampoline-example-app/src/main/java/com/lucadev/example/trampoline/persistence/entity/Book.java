package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.model.User;
import lombok.*;

import javax.persistence.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Entity
@Table(name = "EXAMPLE_APP_BOOK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book extends TrampolineEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_author")
    private User author;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
