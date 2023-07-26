package me.janek.dairy.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.janek.dairy.common.exception.InvalidEntityParameterException;
import me.janek.dairy.common.util.TokenGenerator;
import me.janek.dairy.domain.BaseEntity;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private static final String PREFIX_USER_ENTITY = "user_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userToken;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Builder
    public User(String email, String password) {
        if (!StringUtils.hasText(email)) throw new InvalidEntityParameterException("이메일이 존재하지 않습니다.");
        if (!StringUtils.hasText(password)) throw new InvalidEntityParameterException("비밀번호가 존재하지 않습니다.");

        this.userToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_USER_ENTITY);
        this.email = email;
        this.password = password;
    }

}
