package nc.backend.controllers;

import nc.backend.dtos.AuthenticationAnswerDto;
import nc.backend.dtos.AuthenticationRequestDto;
import nc.backend.services.UserAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class UserAuthenticationController {

    private UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthenticationAnswerDto login(@RequestBody AuthenticationRequestDto requestDto) {
        return this.userAuthenticationService.login(requestDto);
    }
}
