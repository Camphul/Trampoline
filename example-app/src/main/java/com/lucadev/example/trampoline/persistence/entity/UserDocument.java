package com.lucadev.example.trampoline.persistence.entity;

import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.data.entity.TrampolineEntity;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_document")
public class UserDocument extends TrampolineEntity {

	// The user who persisted this blogpost.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // Delete blogpost when user is deleted.
	@JoinColumn(name = "author_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_user_document_author_id_user_id"))
	private User author;

	// The user who persisted this blogpost.
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // Delete blogpost when user is deleted.
	@JoinColumn(name = "asset_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_user_document_asset_id_asset_id"))
	private AssetMetaData asset;

}
