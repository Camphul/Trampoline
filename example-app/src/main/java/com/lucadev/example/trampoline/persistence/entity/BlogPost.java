package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "blogpost")
public class BlogPost extends TrampolineEntity {

	// The user who persisted this blogpost.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_blogpost_author_id_user_id"))
	private User author;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	/*
	 * A blogpost may have 0 or more comments. We use two way relationships so we can make
	 * comments pageable.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "bind_blogpost_blogpost_comment",
			joinColumns = @JoinColumn(name = "blogpost_id", referencedColumnName = "id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fkb_blogpost_id_blogpost_comment")),
			inverseJoinColumns = @JoinColumn(name = "blogpost_comment_id", referencedColumnName = "id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fkb_blogpost_comment_id_blogpost_comment")))
	private Collection<BlogPostComment> comments = new ArrayList<>();

}
