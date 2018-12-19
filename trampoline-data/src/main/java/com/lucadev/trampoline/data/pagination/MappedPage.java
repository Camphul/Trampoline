package com.lucadev.trampoline.data.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A custom {@link Page} implementation.
 * <p>
 * This class allows you to easily map a {@link Page} type into another type.
 * Useful when wanting to return a page of DTOs instead of JPA entities in your REST endpoint.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 8-12-18
 */
public class MappedPage<R> extends PageImpl<R> {

    /**
     * Maps a given page.
     *
     * @param original    the original Page before
     * @param pageable    the {@link Pageable}
     * @param mapFunction the function to transform the contents of the page.
     * @param <T>         the type of the original page.
     */
    public <T> MappedPage(Page<T> original, Pageable pageable, Function<T, R> mapFunction) {
        super(original.getContent().stream().map(mapFunction).collect(Collectors.toList()),
                pageable, original.getTotalElements());
    }

    /**
     * Static method which just invokes the constructor.
     * Might make your code look nicer.
     *
     * @param original    the original Page before
     * @param mapFunction the function to transform the contents of the page.
     * @param <T>         the type of the original page.
     * @param <R>         the content type of the new page.
     * @return a mapped page.
     */
    public static <T, R> MappedPage<R> of(Page<T> original, Function<T, R> mapFunction) {
        return new MappedPage<>(original, original.getPageable(), mapFunction);
    }
}
