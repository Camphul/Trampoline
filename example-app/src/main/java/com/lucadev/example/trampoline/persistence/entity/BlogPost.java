package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Blogpost entity.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BLOGPOST")
public class BlogPost extends TrampolineEntity {

	// The user who persisted this blogpost.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogpost_author")
	private User author;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	/*
	 * A blogpost may have 0 or more comments. We use two way relationships so we can make
	 * comments pageable.
	 */
	@OneToMany(orphanRemoval = true)
	private Collection<BlogPostComment> comments = new ArrayList<>();

}
