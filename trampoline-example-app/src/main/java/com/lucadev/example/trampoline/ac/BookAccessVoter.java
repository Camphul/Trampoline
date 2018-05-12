package com.lucadev.example.trampoline.ac;

import com.lucadev.example.trampoline.persistence.entity.Book;
import com.lucadev.trampoline.security.ac.TrampolineAccessVoter;
import com.lucadev.trampoline.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 12-5-18
 */
@Service
public class BookAccessVoter extends TrampolineAccessVoter<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAccessVoter.class);

    public BookAccessVoter() {
        super(Book.class);
    }

    @Override
    public int vote(User user, Book book, Collection<ConfigAttribute> configAttributes) {
        LOGGER.info("Voting {} for book {}", user.getUsername(), book.getName());
        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        LOGGER.info("String attribute: {} from type {}", configAttribute.getAttribute(), configAttribute.getClass().getName());
        return true;
    }
}
