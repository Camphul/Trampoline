package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Another entity defining a comments on a blog post.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Entity
@Table(name = "BLOGPOST_COMMENT")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogPostComment extends TrampolineEntity {

    @Setter
	@ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogpost_id")
    private BlogPost blogPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogpost_comment_author")
    private User author;

    @Column(name = "content", nullable = false)
    private String content;

    public BlogPostComment(User author, String content) {
        this.author = author;
        this.content = content;
    }
}
