package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.model.User;
import lombok.*;

import javax.persistence.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Entity
@Table(name = "BLOGPOST_COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogPostComment extends TrampolineEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogpost_comment_author")
    private User author;

    @Column(name = "content", nullable = false)
    private String content;
}
