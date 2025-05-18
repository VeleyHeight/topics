package com.example.demo;

import com.example.demo.model.UsersRoles;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println(passwordEncoder.encode("User"));
//		System.out.println(passwordEncoder.encode("Admin"));
//		System.out.println(passwordEncoder.encode("UserAndAdmin"));
		SpringApplication.run(DemoApplication.class, args);
		System.out.println();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}
