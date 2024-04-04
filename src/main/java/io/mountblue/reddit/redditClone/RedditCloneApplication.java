package io.mountblue.reddit.redditClone;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class RedditCloneApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(RedditCloneApplication.class, args);
	}
}
