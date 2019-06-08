package com.lucadev.example.trampoline.web.model.mapper;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.example.trampoline.web.model.BlogPostDto;
import com.lucadev.example.trampoline.web.model.BlogPostSummaryDto;
import com.lucadev.example.trampoline.web.model.CreateBlogPostRequest;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for blogposts.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
@Mapper(uses = { BlogPostCommentMapper.class, UserMapper.class })
public interface BlogPostMapper {

	BlogPostDto toDto(BlogPost blogPost);

	BlogPostSummaryDto toSummaryDto(BlogPost blogPost);

	BlogPost fromRequest(CreateBlogPostRequest request);

}
