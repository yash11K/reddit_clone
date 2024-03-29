package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.User;

public interface UserService {
    void createUser(User user);

    User mapDtoToEntity(UserDto userDto);

    User getUserById(Long userId);

    UserDto mapEntityToDto(User user);

    void updateUser(User updatedUser);

    void deleteUser(Long userId);
}
