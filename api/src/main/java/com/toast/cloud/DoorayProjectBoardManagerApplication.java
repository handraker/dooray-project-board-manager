package com.toast.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.toast.cloud")
@SpringBootApplication
public class DoorayProjectBoardManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoorayProjectBoardManagerApplication.class);
    }

}
