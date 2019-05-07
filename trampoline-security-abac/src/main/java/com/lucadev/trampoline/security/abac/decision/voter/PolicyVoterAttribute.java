package com.lucadev.trampoline.security.abac.decision.voter;

import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;

/**
 * A {@link ConfigAttribute} for voting.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Data
@RequiredArgsConstructor
public class PolicyVoterAttribute implements ConfigAttribute {

	private final SecurityAccessContext securityAccessContext;

	public String getAttribute() {
		return getAction();
	}

	public String getAction() {
		return (String)securityAccessContext.getAction();
	}
}
