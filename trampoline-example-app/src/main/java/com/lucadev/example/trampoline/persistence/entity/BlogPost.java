package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The entity for the blog post.
 * Extends a {@link TrampolineEntity} to enable auditing and {@link java.util.UUID} id.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Entity
@Table(name = "BLOGPOST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogPost extends TrampolineEntity {

    //The user who persisted this blogpost.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogpost_author")
    private User author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    //A blogpost may have 0 or more comments.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "BLOGPOST_COMMENT_RELATION",
            joinColumns = @JoinColumn(
                    name = "blogpost_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "blogpost_comment_id", referencedColumnName = "id"))
    private Collection<BlogPostComment> comment = new ArrayList<>();
}
