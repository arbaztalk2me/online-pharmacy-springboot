package com.arbaz.demo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmacyApplication {
	
	private static final Logger logger = LogManager.getLogger(PharmacyApplication.class); 
	public static void main(String[] args) {
		SpringApplication.run(PharmacyApplication.class, args);
		BasicConfigurator.configure();  
		  logger.info("Hello world");  
		  logger.info("we are in logger info mode"); 
		  
	}

}
