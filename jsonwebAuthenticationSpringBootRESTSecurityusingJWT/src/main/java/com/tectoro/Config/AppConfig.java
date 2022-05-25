package com.tectoro.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AppConfig 
{
	@Bean
	public BCryptPasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
		
	}
	

}
