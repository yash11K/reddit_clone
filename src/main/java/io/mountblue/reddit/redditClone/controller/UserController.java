package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.UserManager;
import io.mountblue.reddit.redditClone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserManager userService) {
        this.userService = userService;
    }

    @PostMapping("/new/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.mapDtoToEntity(userDto);
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }
        UserDto userDto = userService.mapEntityToDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto updatedUserDto) {

        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        User updatedUser = userService.mapDtoToEntity(updatedUserDto);
        updatedUser.setUserId(userId);
        userService.updateUser(updatedUser);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
