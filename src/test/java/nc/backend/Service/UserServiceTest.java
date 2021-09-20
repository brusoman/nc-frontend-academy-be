package nc.backend.Service;

import nc.backend.common.utils.ValidationException;
import nc.backend.daos.UserDao;
import nc.backend.services.UserDataService;
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
public class UserServiceTest {
    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private UserService userService;


    @Test
    public void getUserByIDTest(){
        assertTrue(userService.getUser(1L).getUser_id() == 1L);
        assertTrue(userService.getUser(132L) == null);
        logger.info("----------getByIDTest OK----------");
    }

    @Test
    public void getUserByLoginTest(){
        assertTrue(userService.findByUserLogin("lalal").getId() == 1L);
        assertTrue(userService.findByUserLogin("ascasca") == null);
        logger.info("----------getUserByLoginTest OK----------");
    }

    @Test
    public void getUserByEmail(){
        assertTrue(userService.getUserByEmail("email").getUser_id() == 1L);
        assertTrue(userService.getUserByEmail("ewfwefwef") == null);
        logger.info("----------getUserByEmail OK----------");
    }

}