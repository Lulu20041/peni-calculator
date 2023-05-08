package com.example.tamozhpenies;

import com.example.tamozhpenies.peni.OldPeniService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class PeniServiceTests {
    @Autowired
    OldPeniService peniService;
    @Test
    private void testCalculate() {


    }
}
