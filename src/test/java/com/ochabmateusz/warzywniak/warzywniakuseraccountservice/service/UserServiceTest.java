package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.service;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.*;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.WuserMongoRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserServiceTest {

    private User user;
    private UserBase userBase;


    private final String userName="Frank";
    private final String userPassword="Pigeon12345";
    private final String userNickname="Frankbank";
    private final String userCompanyName="xxx";
    private final String userCity="krakow";
    private final String userStreet="mainStreet";
    private final String userId="1234567890";
    private final String userStarDatePremium="startdatePremium";
    private final String userCreatedDate="createdDate";
    private final String userEndDatePremium="";





    @InjectMocks
    UserService userService;

    @Mock
    WuserMongoRepository wuserMongoRepository;


    @BeforeEach
    void setUp() {

        userBase = new UserBase(userName, userPassword, userNickname, userCompanyName, new UserEmail(),
                new Location(userCity, new Coordinates("12.432", "13.356"), userStreet, 12));
        user = new User(userId, userBase, false, false, Role.FREE, new Premium(false, userStarDatePremium, userEndDatePremium),
                new ArrayList<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>(), userCreatedDate, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());


    }

    @AfterEach
    void cleanUp() {


    }


    //test if method returns the object
    @Test
    void returnUserFromDb() throws NotFoundException {

        user.setConfirmed(true);
        when(wuserMongoRepository.findById(userId)).thenReturn(java.util.Optional.ofNullable(user));


        User user1 = userService.returnUserFromDb(userId);


        assertTrue(user1.isConfirmed());
        assertEquals(userName, user1.getUserBase().getName());
        assertFalse(user1.isSuperUser());
    }

    @Test
    void returnUserFromDb_ReturnsNullShouldThrowException() {

        when(wuserMongoRepository.findById(userId)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    userService.returnUserFromDb(userId);
                }
        );

        assertEquals("User Not Found", responseStatusException.getReason());
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatus());
    }


    @Test
    void confirmUserAndReturn() {

//asserts for input object
        assertFalse(user.isConfirmed());
        assertEquals(0, user.getModifiedDates().size());

//call tested method
        User userLocal = userService.confirmUserAndReturn(user);

//assert for returned object
        assertTrue(userLocal.isConfirmed());
        assertEquals(1, userLocal.getModifiedDates().size());
        List<ModifiedDate> list = userLocal.getModifiedDates();
        assertEquals(ModificationType.CONFIRMATION, list.get(0).getModificationType());
        assertFalse( list.get(0).getModifiedDate().isEmpty());
    }

    @Test
    void changeUsername(){
//asserts for input object
        assertEquals(userName,user.getUserBase().getName());
        assertEquals(0, user.getModifiedDates().size());

//call tested method
        User userLocal = userService.changeUsername(user,"Adam");


//asserts for returned object
        assertEquals("Adam",userLocal.getUserBase().getName());
        List<ModifiedDate> list = userLocal.getModifiedDates();
        assertEquals(1, list.size());
        assertEquals(ModificationType.NAME, list.get(0).getModificationType());
        assertFalse( list.get(0).getModifiedDate().isEmpty());
    }

    @Test
    void changePassword(){

        //asserts for input object
        assertTrue(UserBase.passwordEncoder().matches(this.userPassword,user.getUserBase().getPassword()));


//call tested method
        User userLocal = this.userService.changePassword(this.user,"Tom123");

        assertTrue(UserBase.passwordEncoder().matches("Tom123",userLocal.getUserBase().getPassword()));
        List<ModifiedDate> list = userLocal.getModifiedDates();
        assertEquals(1, list.size());
        assertEquals(ModificationType.PASSWORD, list.get(0).getModificationType());
        assertFalse( list.get(0).getModifiedDate().isEmpty());

    }





}