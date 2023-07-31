package me.janek.dairy.domain.user;

import lombok.RequiredArgsConstructor;
import me.janek.dairy.common.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder;

    private final UserStore userStore;

    private final UserReader userReader;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var findUser = userReader.getUserByEmail(email);
        return new User(findUser.getEmail(), findUser.getPassword(), new ArrayList<>());
    }

    @Override
    @Transactional
    public UserInfo sighUpUser(UserCommand command) {
        if (userReader.isUserExists(command.getEmail())) throw new UserAlreadyExistException();

        var encodedPassword = encoder.encode(command.getPassword());
        var initUser = command.toEntity(encodedPassword);

        userStore.store(initUser);

        return UserInfo.from(initUser);
    }

}
