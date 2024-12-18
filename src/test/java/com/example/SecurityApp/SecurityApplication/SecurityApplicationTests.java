package com.example.SecurityApp.SecurityApplication;

import com.example.SecurityApp.SecurityApplication.entities.User;
import com.example.SecurityApp.SecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {
		User user=new User(4L,"saurav09@gmail.com","saurav123");
		String token= jwtService.generateToken(user);
		System.out.println(token);
		Long userId= jwtService.getUserIdFromToken(token);
		System.out.println(userId);
	}

}
