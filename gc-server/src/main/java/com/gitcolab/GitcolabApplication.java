package com.gitcolab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitcolabApplication {
	private static Logger logger = LoggerFactory.getLogger(GitcolabApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GitcolabApplication.class, args);
		logger.debug("GitColab Application is running...");

	}

}
