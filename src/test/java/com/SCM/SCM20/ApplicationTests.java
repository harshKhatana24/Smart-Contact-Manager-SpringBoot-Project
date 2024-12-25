package com.SCM.SCM20;

import com.SCM.SCM20.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmailTest(){
		emailService.sendEmail(
				"hkhatanab2004@gmail.com",
				"just testing email service",
				"this is scm project working on email service"
		);
	}

}
