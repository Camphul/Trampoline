package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.persistence.entity.UserDocument;
import com.lucadev.example.trampoline.persistence.repository.UserDocumentRepository;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDocumentService {

	private final UserDocumentRepository repository;
	private final AssetStore assetStore;

	public Page<UserDocument> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Page<UserDocument> findAllByUser(User author, Pageable pageable) {
		return this.repository.findByAuthor(author, pageable);
	}

	@Transactional
	public UserDocument create(User author, byte[] data, AssetMetaData assetMetaData) {
		assetMetaData = this.assetStore.put(data, assetMetaData);
		UserDocument document = new UserDocument();
		document.setAuthor(author);
		document.setAsset(assetMetaData);
		return this.repository.save(document);
	}

	@Transactional
	public void remove(UserDocument userDocument) {
		this.repository.delete(userDocument);
	}
}
