package nc.backend.controllers;

import nc.backend.dtos.UserDto;
import nc.backend.dtos.UserRegistrationDto;
import nc.backend.entities.User;
import nc.backend.services.UserRegistrationService;
import nc.backend.services.UserDataService;
import nc.backend.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserDataService userDataService;

    private BCryptPasswordEncoder encoder;

    public UserController(UserService userService, UserDataService userDataService) {
        this.userService = userService;
        this.userDataService = userDataService;
    }


    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }


    @GetMapping("/email/{userEmail}")
    public UserDto getUser(@PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);
    }

    @GetMapping("/user/{login}")
    public UserDto getUserByLogin(@PathVariable String login){
        return userDataService.buildUserDtoFromUser(userService.findByUserLogin(login));
    }
}
