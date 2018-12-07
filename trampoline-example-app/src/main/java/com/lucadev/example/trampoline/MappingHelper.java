package com.lucadev.example.trampoline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class MappingHelper {

    /**
     * Map page to other type, for example to use dto instead of entity.
     *
     * @param original
     * @param pageable
     * @param mapFunc
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Page<R> mapPage(Page<T> original, Pageable pageable, Function<T, R> mapFunc) {
        return new PageImpl<>(original.getContent().stream().map(mapFunc).collect(Collectors.toList()), pageable, original.getTotalElements());
    }

}
