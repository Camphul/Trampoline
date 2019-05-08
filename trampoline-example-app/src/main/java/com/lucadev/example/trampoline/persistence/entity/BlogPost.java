package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The entity for the blog post.
 * Extends a {@link TrampolineEntity} to enable auditing and {@link java.util.UUID} id.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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
	@OneToMany(
			orphanRemoval = true
	)
	private Collection<BlogPostComment> comments = new ArrayList<>();
}
