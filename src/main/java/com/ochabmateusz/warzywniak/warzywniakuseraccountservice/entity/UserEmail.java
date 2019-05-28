package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class UserEmail {


    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "enter valid email adress")
    @NotEmpty
    @Indexed(unique = true)
    @Getter
    @Field(value = "activationEmail")
    private String activationEmail;


    @Getter
    @Setter
    @Pattern(regexp = "^[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "enter valid email adress")
    @Field(value = "activeEmail")
    private String activeEmail;


    @Setter
    @Getter
    @Pattern(regexp = "^[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "enter valid email adress")
    @Field(value = "waitingEmail")
    private String waitingEmail ;


    @Setter
    @Getter
    @Field(value = "listOfPreviousEmails")
    private List<String> listOfPreviousEmails = new ArrayList<>();


}