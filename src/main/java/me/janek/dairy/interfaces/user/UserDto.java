package me.janek.dairy.interfaces.user;

import lombok.Data;
import me.janek.dairy.domain.user.UserInfo;

import java.time.format.DateTimeFormatter;

public class UserDto {

    @Data
    public static class Join {

        private String email;

        private String password;

    }

    @Data
    public static class Info {

        private String userToken;

        private String email;

        private String joinDate;

        private String lastModifiedDate;

        public Info(UserInfo userInfo) {
            this.userToken = userInfo.getUserToken();
            this.email = userInfo.getEmail();
            this.joinDate = userInfo.getJoinDate().format(DateTimeFormatter.ISO_DATE);
            this.lastModifiedDate = userInfo.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE);
        }

    }

}
