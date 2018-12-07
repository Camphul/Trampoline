package com.lucadev.example.trampoline.model;

import com.lucadev.trampoline.model.SuccessResponse;
import lombok.Getter;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
public class CreateBlogPostCommentResponse extends SuccessResponse {

    private final UUID id;

    public CreateBlogPostCommentResponse(UUID id, boolean success, String message) {
        super(success, message);
        this.id = id;
    }
}
