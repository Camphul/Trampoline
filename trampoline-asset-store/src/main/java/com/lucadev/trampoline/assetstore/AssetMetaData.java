package com.lucadev.trampoline.assetstore;

import com.lucadev.trampoline.data.entity.TrampolineEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * Asset meta data entity which acts as a reference to the binary data.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Entity
@Table(name = "trampoline_asset_meta_data")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssetMetaData extends TrampolineEntity {

	@Setter
	@NotBlank
	@NotEmpty
	@Column(name = "asset_name", nullable = false)
	private String name;

	@NotBlank
	@NotEmpty
	@Column(name = "original_filename", nullable = false)
	private String originalFilename;

	@NotBlank
	@NotEmpty
	@Column(name = "content_type")
	private String contentType;

	@Positive
	@Column(name = "file_size")
	private long fileSize;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AssetMetaData)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		AssetMetaData that = (AssetMetaData) o;

		if (this.fileSize != that.fileSize) {
			return false;
		}
		if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
			return false;
		}
		if (this.originalFilename != null
				? !this.originalFilename.equals(that.originalFilename)
				: that.originalFilename != null) {
			return false;
		}

		return this.contentType != null ? this.contentType.equals(that.contentType)
				: that.contentType == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		result = 31 * result
				+ (this.originalFilename != null ? this.originalFilename.hashCode() : 0);
		result = 31 * result
				+ (this.contentType != null ? this.contentType.hashCode() : 0);
		result = 31 * result + (int) (this.fileSize ^ (this.fileSize >>> 32));
		return result;
	}

}
