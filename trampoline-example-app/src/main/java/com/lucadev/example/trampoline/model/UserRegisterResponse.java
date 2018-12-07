package com.lucadev.example.trampoline.model;

import com.lucadev.trampoline.model.SuccessResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class UserRegisterResponse extends SuccessResponse {

    private final UUID id;

    public UserRegisterResponse(UUID uuid, boolean success, String message) {
        super(success, message);
        this.id = uuid;
    }
}
