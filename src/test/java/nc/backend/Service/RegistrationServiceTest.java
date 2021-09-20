package nc.backend.Service;

import nc.backend.daos.UserDao;
import nc.backend.dtos.UserRegistrationDto;
import nc.backend.services.UserDataService;
import nc.backend.services.UserRegistrationService;
import nc.backend.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest

public class RegistrationServiceTest {
    private static Logger logger = LoggerFactory.getLogger(RegistrationServiceTest.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegistrationService userRegistrationService;

    @Test
    public void registerUserTest(){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto
                ("testPass2", "testLogin2", "testEmail2", "testName2", "testSurname2", false);
        userRegistrationService.registerUser(userRegistrationDto);
        Long userID = userService.findByUserLogin("testLogin2").getId();
        //logger.info("---------userID------------" + userID);
        userRegistrationService.deleteUserById(userID);
        assertTrue(userService.getUser(userID) == null);
        logger.info("---------registerUserTest OK------------");
    }
}
