package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.web.model.BlogPostDto;
import com.lucadev.example.trampoline.web.model.BlogPostSummaryDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostRequest;
import org.mapstruct.Mapper;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Mapper(componentModel = "spring", uses = {BlogPostCommentMapper.class, UserMapper.class})
public interface BlogPostMapper {

	BlogPostDto toDto(BlogPost blogPost);

	BlogPostSummaryDto toSummaryDto(BlogPost blogPost);

	BlogPost fromRequest(CreateBlogPostRequest request);
}
