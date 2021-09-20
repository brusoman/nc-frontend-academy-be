package nc.backend.controllers;

import nc.backend.dtos.UserDto;
import nc.backend.dtos.UserRegistrationDto;
import nc.backend.services.UserRegistrationService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class UserRegistrationController {


    private UserRegistrationService userAuthorizationService;

    public UserRegistrationController(UserRegistrationService userAuthorizationService) {
        this.userAuthorizationService = userAuthorizationService;
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserDto register(@RequestBody UserRegistrationDto userRegistrationObject) {
        return this.userAuthorizationService.registerUser(userRegistrationObject);
    }
}
