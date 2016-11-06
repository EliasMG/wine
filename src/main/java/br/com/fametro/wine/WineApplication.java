package br.com.fametro.wine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WineApplication {

	public static void main(String[] args) {
		SpringApplication.run(WineApplication.class, args);
		
		//BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();
		//System.out.println(encoder.encode("admin"));
	}
}
