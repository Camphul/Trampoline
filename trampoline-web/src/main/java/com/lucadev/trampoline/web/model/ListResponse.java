package com.lucadev.trampoline.web.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

/**
 * Wrapper for {@link List} to wrap data.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Getter
@ToString
@EqualsAndHashCode
public class ListResponse {

	private final List content;

	private final int size;

	private final boolean empty;

	/**
	 * Construct list response.
	 * @param list list to wrap.
	 */
	public ListResponse(@NonNull List list) {
		this.content = list;
		this.size = list.size();
		this.empty = list.isEmpty();
	}

}
