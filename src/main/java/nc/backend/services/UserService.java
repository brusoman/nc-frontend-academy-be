package nc.backend.services;

import lombok.extern.slf4j.Slf4j;
import nc.backend.daos.UserDao;
import nc.backend.dtos.UserDto;
import nc.backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {
    //@Autowired
    private UserDao userDao;
    private UserDataService userDataService;
    private static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    public UserService(UserDao userDao, UserDataService userDataService) {
        this.userDao = userDao;
        this.userDataService = userDataService;
    }

    public UserDto getUser(Long userId){
        logger.info("----------try to getUserByID-----------");
        User user = userDao.findByID(userId);
        return this.userDataService.buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail){
        logger.info("----------try to getUserByEmail " + userEmail);
        User user = userDao.findByEmail(userEmail);
        UserDto userDto = this.userDataService.buildUserDtoFromUser(user);
        return this.userDataService.buildUserDtoFromUser(user);
    }

    public User findByUserLogin(String login){
        User user = this.userDao.findByUserLogin(login);
        System.out.println(user.getLogin());
        return user;
    }

}