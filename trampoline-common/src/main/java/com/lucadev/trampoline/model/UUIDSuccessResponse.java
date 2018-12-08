package com.lucadev.trampoline.model;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * A {@link SuccessResponse} which contains a {@link UUID}.
 * This is useful when wanting to reference an id and a success state.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 8-12-18
 */
@Getter
@ToString
public class UUIDSuccessResponse extends SuccessResponse {

    private final UUID id;

    /**
     * Construct a {@code UUIDSuccessResponse}
     * The message in this case depends on the success.
     * If success is true it will default to "ok" else "error"
     *
     * @param id      the id to pass to the client.
     * @param success if the action was completed with success.
     */
    public UUIDSuccessResponse(UUID id, boolean success) {
        super(success);
        this.id = id;
    }

    /**
     * Construct a {@code UUIDSuccessResponse}
     *
     * @param id      the id to pass
     * @param success if the action was completed with success.
     * @param message the message related to the success state of this response.
     */
    public UUIDSuccessResponse(UUID id, boolean success, String message) {
        super(success, message);
        this.id = id;
    }
}
