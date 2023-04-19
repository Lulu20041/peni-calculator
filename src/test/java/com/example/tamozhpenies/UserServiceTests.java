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
    @Test
    private void testCalculate() {
        //given
        Date taxDate = new Date("2023/02/17");
        double taxSum = 50;
        Date peniDate = new Date("2023/04/14");
        //when
        peniService.calculatePenies(taxDate,taxSum,peniDate);
        System.out.println(peniService.getPenies());
        WritableAssertionInfo info = assertThat(peniService.getPenies()).info;
    }

}
