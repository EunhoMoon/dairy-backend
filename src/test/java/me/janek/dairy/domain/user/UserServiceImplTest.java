package me.janek.dairy.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("")
    void test() {
        //given
        var command = UserCommand.builder()
            .email("test@co.kr")
            .password("1234")
            .build();

        // when
        UserInfo userInfo = userService.sighUpUser(command);

        System.out.println("userInfo.getUserToken() = " + userInfo.getUserToken());
        
        // then
        assertEquals(userInfo.getEmail(), command.getEmail());
    }

}