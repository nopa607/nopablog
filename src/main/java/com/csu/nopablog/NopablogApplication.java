package com.csu.nopablog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.csu.nopablog.dao")
public class NopablogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NopablogApplication.class, args);
    }

}
