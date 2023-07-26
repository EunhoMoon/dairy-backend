package me.janek.dairy.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class UserInfo {

    private final String userToken;

    private final String email;

    private final ZonedDateTime joinDate;

    private final ZonedDateTime lastModifiedDate;

    @Builder
    private UserInfo(String userToken, String email, ZonedDateTime joinDate, ZonedDateTime lastModifiedDate) {
        this.userToken = userToken;
        this.email = email;
        this.joinDate = joinDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static UserInfo from(User user) {
        return UserInfo.builder()
            .userToken(user.getUserToken())
            .email(user.getEmail())
            .joinDate(user.getCreatedAt())
            .lastModifiedDate(user.getUpdatedAt())
            .build();
    }

}
