package com.lucadev.example.trampoline.web.controller;

import com.lucadev.example.trampoline.persistence.entity.UserDocument;
import com.lucadev.example.trampoline.service.UserDocumentService;
import com.lucadev.example.trampoline.web.model.UserDocumentDto;
import com.lucadev.example.trampoline.web.model.mapper.UserDocumentMapper;
import com.lucadev.trampoline.assetstore.AssetMetaData;
import com.lucadev.trampoline.data.MappedPage;
import com.lucadev.trampoline.data.web.annotation.FindById;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.web.model.StringValueDto;
import com.lucadev.trampoline.web.model.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/10/19
 */
@Slf4j
@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class UserDocumentController {

	private final UserDocumentMapper mapper;
	private final UserDocumentService service;
	private final UserService userService;

	@GetMapping
	public Page<UserDocumentDto> findAll(Pageable pageable) {
		return MappedPage.of(this.service.findAll(pageable), mapper::toDto);
	}

	@GetMapping("/me")
	public Page<UserDocumentDto> findAllByPrincipal(Pageable pageable) {
		return MappedPage.of(this.service.findAllByUser(this.userService.currentUserOrThrow(), pageable), mapper::toDto);
	}

	@PostMapping
	public UserDocumentDto submit(@RequestBody StringValueDto valueDto) {
		log.debug("Creating textdoc");
		byte[] data = valueDto.getValue().getBytes();
		AssetMetaData assetMetaData = new AssetMetaData();
		assetMetaData.setContentType("text/plain");
		assetMetaData.setFileSize(data.length);
		assetMetaData.setName("textdoc.txt");
		assetMetaData.setOriginalFilename("textdoc.txt");
		UserDocument document = this.service.create(this.userService.currentUserOrThrow(), data, assetMetaData);
		return mapper.toDto(document);
	}

	@DeleteMapping("/{userDocument}")
	public SuccessResponse delete(@FindById UserDocument userDocument) {
		this.service.remove(userDocument);
		return new SuccessResponse();
	}


}
