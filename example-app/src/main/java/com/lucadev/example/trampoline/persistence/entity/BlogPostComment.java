package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Blogpost comment entity.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "blogpost_comment",
		indexes = @Index(name = "idx_blogpost_comment_blogpost_id",
				columnList = "blogpost_id"))
public class BlogPostComment extends TrampolineEntity {

	@ToString.Exclude // Exclude since we dont want infinite loops.
	@EqualsAndHashCode.Exclude // Exclude since we dont want infinite loops.
	@ManyToOne(fetch = FetchType.LAZY/* Prevent recursive lookup */)
	@OnDelete(action = OnDeleteAction.CASCADE) // Delete comment when blogpost is deleted.
	@JoinColumn(name = "blogpost_id", nullable = false, updatable = false,
			foreignKey = @ForeignKey(name = "fk_blogpost_comment_blogpost_id"))
	private BlogPost blogPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE) // Delete comment when used is deleted.
	@JoinColumn(name = "author_id", nullable = false, updatable = false,
			foreignKey = @ForeignKey(name = "fk_blogpost_comment_author_id_user_id"))
	private User author;

	@Column(name = "content", nullable = false)
	private String content;

	/**
	 * Construct simple comment.
	 * @param author comment author.
	 * @param content text content.
	 */
	public BlogPostComment(User author, String content) {
		this.author = author;
		this.content = content;
	}

}
