package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.persistence.entity.BlogPostComment;
import com.lucadev.example.trampoline.web.model.BlogPostCommentDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostCommentRequest;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import org.mapstruct.Mapper;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Mapper(uses = UserMapper.class)
public interface BlogPostCommentMapper {

	BlogPostCommentDto toDto(BlogPostComment comment);

	BlogPostComment fromRequest(CreateBlogPostCommentRequest request);

}
