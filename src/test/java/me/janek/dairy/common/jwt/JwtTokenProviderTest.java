package me.janek.dairy.common.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Test
    @DisplayName("test")
    void test() {
        //given
        String userEmail = "test@co.kr";
        
        // when
        var token = jwtTokenProvider.createToken(userEmail);
        
        // then
        System.out.println("token = " + token);

        String decodeEmail = jwtTokenProvider.getAuthentication(token);
        System.out.println("decodeEmail = " + decodeEmail);
    }
    
}