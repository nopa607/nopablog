package com.csu.nopablog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.Date;

@SpringBootTest
class NopablogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getLongTime() {
        Date now = new Date();
        System.out.println("==========================");
        System.out.println(now.getTime());
        System.out.println(now.getTime()/1000);
        System.out.println("==========================");
    }

}
