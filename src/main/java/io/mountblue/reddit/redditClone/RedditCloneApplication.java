package io.mountblue.reddit.redditClone;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class RedditCloneApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(RedditCloneApplication.class, args);
	}

}
