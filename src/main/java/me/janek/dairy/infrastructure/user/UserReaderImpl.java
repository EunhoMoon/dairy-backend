package me.janek.dairy.infrastructure.user;

import lombok.RequiredArgsConstructor;
import me.janek.dairy.common.exception.UserNotFoundException;
import me.janek.dairy.domain.user.User;
import me.janek.dairy.domain.user.UserReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
