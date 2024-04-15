package io.mountblue.reddit.redditClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class RedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}
}
