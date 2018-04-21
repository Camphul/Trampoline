package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.repository.UserRepository;
import com.lucadev.trampoline.security.service.AbstractUserService;
import com.lucadev.trampoline.service.time.TimeProvider;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UserServiceImpl extends AbstractUserService {

    private final TimeProvider timeProvider;

    public UserServiceImpl(UserRepository userRepository, TimeProvider timeProvider) {
        super(userRepository);
        this.timeProvider = timeProvider;
    }

    @Override
    public User updateLastSeen(User user) {
        user.setLastSeen(timeProvider.now());
        return getUserRepository().save(user);
    }
}
