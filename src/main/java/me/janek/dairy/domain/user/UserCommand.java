package me.janek.dairy.domain.user;

import lombok.Builder;
import lombok.Getter;

import static me.janek.dairy.interfaces.user.UserDto.Join;

@Getter
public class UserCommand {

    private final String email;

    private final String password;

    @Builder
    private UserCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
            .email(this.email)
            .password(encodedPassword)
            .build();
    }

    public static UserCommand from(Join request) {
        return UserCommand.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .build();
    }

}
