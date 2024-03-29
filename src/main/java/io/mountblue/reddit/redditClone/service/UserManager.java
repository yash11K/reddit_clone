package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dao.UserRepository;
import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
    @Override
    public User mapDtoToEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .topics(userDto.getInterests())
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

}
