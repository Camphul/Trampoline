package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Blogpost comment entity.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "BLOGPOST_COMMENT")
public class BlogPostComment extends TrampolineEntity {

	@ToString.Exclude // Exclude since we dont want infinite loops.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogpost_id")
	private BlogPost blogPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogpost_comment_author")
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
