package nc.backend.services;


import nc.backend.dtos.UserDto;
import nc.backend.dtos.UserRegistrationDto;
import nc.backend.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    public UserDto buildUserDtoFromUser(User user){
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setAdmin(user.getAdmin());
        userDto.setSurname(user.getSurname());
        userDto.setUser_id(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());

        return userDto;
    }

}
