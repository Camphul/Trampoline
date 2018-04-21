package com.lucadev.trampoline.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@ToString
public class SuccessResponse extends MessageResponse {

    private final boolean success;

    public SuccessResponse(boolean success) {
        super("");
        this.success = success;
    }

    public SuccessResponse(boolean success, String message) {
        super(message);
        this.success = success;
    }
}
