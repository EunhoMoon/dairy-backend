package me.janek.dairy.infrastructure.user;

import lombok.RequiredArgsConstructor;
import me.janek.dairy.domain.user.User;
import me.janek.dairy.domain.user.UserStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;

    @Override
    public void store(User initUser) {
        userRepository.save(initUser);
    }
}
