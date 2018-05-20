package com.lucadev.trampoline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A simple model to return a String message as a response.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@AllArgsConstructor
public class MessageResponse {

    private final String message;

}
