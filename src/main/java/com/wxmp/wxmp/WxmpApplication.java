package com.wxmp.wxmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wahaha
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.fulihui")
public class WxmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxmpApplication.class, args);
	}

}
