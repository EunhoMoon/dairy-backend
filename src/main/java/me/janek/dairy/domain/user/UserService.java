package me.janek.dairy.domain.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserInfo sighUpUser(UserCommand command);

}
