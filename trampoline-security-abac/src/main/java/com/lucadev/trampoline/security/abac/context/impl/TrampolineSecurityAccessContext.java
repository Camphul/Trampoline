package com.lucadev.trampoline.security.abac.context.impl;

import com.lucadev.trampoline.security.abac.context.SecurityAccessContext;
import lombok.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TrampolineSecurityAccessContext implements SecurityAccessContext {
    private Object subject;
    private Object resource;
    private Object action;
    private Object environment;
}