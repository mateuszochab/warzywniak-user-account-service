package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ValidationServiceTest {


    private Map<String, Object> map;

    @InjectMocks
    ValidationService validationService;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
    }





    //test if field in requestbody presents
    @Test
    public void oneValidItemsInRequest() {
        map.put("abc", "hhh");

        assertTrue(validationService.oneValidItemsInRequest("abc", map));
    }

    //tests if exception is thrown when request is empty
    @Test
    public void oneValidItemsInRequest_EmptyRequest() {


        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    validationService.oneValidItemsInRequest("abc", map);
                }
        );
        assertEquals("JSON malformed- request is empty", responseStatusException.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }


    //test if exception is thrown when request does not contains proper body
    @Test
    public void oneValidItemsInRequest_WrongRequest() {
        map.put("asw", "jjj");


        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    validationService.oneValidItemsInRequest("abc", map);
                }
        );
        assertEquals("JSON malformed- request does not contains required fields", responseStatusException.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());

    }

    //test if requestbody contains 2 expected keys
    @Test
    public void twoValidItemsInRequest() {


        map.put("a1", "hhh");
        map.put("a2", "jjj");

        boolean value = validationService.twoValidItemsInRequest("a1", "a2", map);
        assertTrue(value);
    }

    //test if method throw exception when one of two fields is wrong
    @Test
    public void twoValidItemsInRequest_WrongRequest() {


        map.put("a1", "dfdg");
        map.put("a", "fghdf");

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    validationService.twoValidItemsInRequest("a1", "a2", map);
                }

        );

        assertEquals("JSON malformed- atleast one of fields in request is wrong",responseStatusException.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }

    //test if method throw exception when request does not contains 2 required fields
    @Test
    public void twoValidItemsInRequest_WrongRequestNot2Fields() {

        map.put("a1", "dfdg");
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    validationService.twoValidItemsInRequest("a1", "a2", map);
                }

        );

        assertEquals("JSON malformed- request does not contains 2 required fields",responseStatusException.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }



    //test if method throw exception when request is empty
    @DisplayName("twoValidItemsInRequest_EmptyRequest")
    @Test
    public void twoValidItemsInRequest_EmptyRequest() {

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> {
                    validationService.twoValidItemsInRequest("a1", "a2", map);
                }

        );

        assertEquals("JSON malformed- request is empty",responseStatusException.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatus());
    }
}

