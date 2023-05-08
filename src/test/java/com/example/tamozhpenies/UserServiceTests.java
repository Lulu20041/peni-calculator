package com.example.tamozhpenies;

import com.example.tamozhpenies.peni.OldPeniService;
import com.example.tamozhpenies.user.UserService;
import org.assertj.core.api.WritableAssertionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class UserServiceTests {

    @Autowired
    UserService userService;
    @Test
    void login() {
        //given
        String name = "Halz";
        String password = "dana";
        //when
        boolean expected = userService.checkCredentials(name, password);
        //then
        assertThat(expected).isTrue();
    }
    @Autowired
    OldPeniService peniService;


}
