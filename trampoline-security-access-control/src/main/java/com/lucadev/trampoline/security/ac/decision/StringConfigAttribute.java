package com.lucadev.trampoline.security.ac.decision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.ConfigAttribute;

/**
 * A simple String based {@link ConfigAttribute}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 12-5-18
 */
@AllArgsConstructor
@Getter
public class StringConfigAttribute implements ConfigAttribute {

    private final String attribute;

}
