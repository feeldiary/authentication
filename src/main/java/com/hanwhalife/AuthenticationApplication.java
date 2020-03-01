package com.hanwhalife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationApplication 
//		implements CommandLineRunner 
{


	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
/*
	// TODO 삭제 - 테스트로 유저를 넣어서 확인하는 코드입니다.
	@Autowired
	private UserRepository repository;
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		User user = new User();
		user.setUserId("tester");
		user.setPassword(passwordEncoder.encode("tester"));
		repository.save(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
*/
}
