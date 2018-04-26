package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import com.lucadev.trampoline.security.service.AbstractUserService;
import com.lucadev.trampoline.service.time.TimeProvider;

/**
 * {@link com.lucadev.trampoline.security.service.UserService} implementation
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineUserService extends AbstractUserService {

    private final TimeProvider timeProvider;

    public TrampolineUserService(UserRepository userRepository, TimeProvider timeProvider) {
        super(userRepository);
        this.timeProvider = timeProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateLastSeen(User user) {
        user.setLastSeen(timeProvider.now());
        return getUserRepository().save(user);
    }
}
