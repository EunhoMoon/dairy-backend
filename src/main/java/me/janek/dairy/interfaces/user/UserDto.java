package me.janek.dairy.interfaces.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import me.janek.dairy.domain.user.UserInfo;

import java.time.format.DateTimeFormatter;

public class UserDto {

    @Data
    public static class Join {

        @NotEmpty(message = "email을 입력하세요.")
        @Email(message = "잘못된 email 형식입니다.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력하세요.")
        @Pattern(
            regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_+-=])[A-Za-z\\d!@#$%^&*_+-=]{8,}$",
            message="비밀번호는 대/소문자,숫자,특수문자의 조합으로 이루어져야하며 8자리 이상입니다."
        )
        private String password;

        @NotEmpty(message = "비밀번호 확인을 입력하세요.")
        private String confirmedPassword;

        public boolean isPasswordEqual() {
            return confirmedPassword.equals(password);
        }

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
