package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.TopicDto;
import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final TopicService topicService;

    @Autowired
    public UserManager(UserRepository userRepository, TopicService topicService) {
        this.userRepository = userRepository;
        this.topicService=topicService;
    }
    @Override
    public void createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setJoinDate(LocalDateTime.now());
        userRepository.save(user);
    }
    @Override
    public User mapDtoToEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .topics(userDto.getInterests())
                .email(userDto.getEmail())
                .profilePic(userDto.getMediaUri())
                .build();
    }
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    public UserDto mapEntityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .gender(user.getGender())
                .interests(user.getTopics())
                .build();
    }
    @Override
    public void updateUser(User updatedUser) {
        userRepository.save(updatedUser);
    }
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    @Override
    public ResponseEntity<String> updateUserTopics(Long userId, List<TopicDto> topicDtos) {
        User existingUser = getUserById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Topic> existingTopics = existingUser.getTopics();

        for (TopicDto topicDto : topicDtos) {
            Topic existingTopic = topicService.findByName(topicDto.getName());
            if (existingTopic == null) {
                return new ResponseEntity<>("Topic '" + topicDto.getName() + "' does not exist", HttpStatus.BAD_REQUEST);
            }

            boolean topicExists = existingTopics.stream().anyMatch(topic -> topic.getName().equals(topicDto.getName()));
            if (!topicExists) {
                existingTopics.add(existingTopic);
            }
        }

        existingUser.setTopics(existingTopics);
        updateUser(existingUser);

        return new ResponseEntity<>("User topics updated successfully", HttpStatus.OK);
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findByUsername(username);
        if(maybeUser.isEmpty()){
            throw new UsernameNotFoundException("no such username exist");
        }
        User user = maybeUser.get();
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

}

