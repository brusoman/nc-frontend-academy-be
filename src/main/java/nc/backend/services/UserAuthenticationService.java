package nc.backend.services;

import nc.backend.common.Role;
import nc.backend.dtos.AuthenticationAnswerDto;
import nc.backend.dtos.AuthenticationRequestDto;
import nc.backend.entities.User;
import nc.backend.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public UserAuthenticationService(AuthenticationManager authenticationManager,
                                     JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public AuthenticationAnswerDto login(AuthenticationRequestDto requestDto)
            throws UsernameNotFoundException, BadCredentialsException{
        try {
            String username = requestDto.getUsername();
            User user = userService.findByUserLogin(username);
            if ((user == null) || (!BCrypt.checkpw(requestDto.getPassword(), user.getPassword()))) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            List<Role> roles = new ArrayList<>();
            roles.add(new Role(user.getAdmin()));
            String token = jwtTokenProvider.createToken(username, roles);

            return new AuthenticationAnswerDto(username, token);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
