package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication //启动类注解
@EnableTransactionManagement //事务
@EnableScheduling  //用于定时任务
/*@ImportResource("classpath:spring-config-center.xml")*/
public class Study5Application {
	public static void main(String[] args) {
		SpringApplication.run(Study5Application.class, args);
	}

}
