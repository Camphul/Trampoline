package com.lucadev.example.trampoline.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * For now an empty service to manage blog post comments.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Service
@AllArgsConstructor
public class BlogPostCommentService {

    private final BlogPostService blogPostService;

}