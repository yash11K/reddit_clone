package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.TopicDto;
import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    void createUser(User user);

    User mapDtoToEntity(UserDto userDto);

    User getUserById(Long userId);

    UserDto mapEntityToDto(User user);

    void updateUser(User updatedUser);

    void deleteUser(Long userId);
    ResponseEntity<String> updateUserTopics(Long userId, List<TopicDto> topicDtos);
    boolean existsByEmail(String email);
    User findByUsername(String username);
    boolean existsByUsername(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
