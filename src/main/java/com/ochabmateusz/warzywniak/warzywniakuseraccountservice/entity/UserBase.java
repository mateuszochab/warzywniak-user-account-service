package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserBase {


    @Setter
    @Getter
    @NotNull
    @NotEmpty
    @Size(min = 2, message = "field Name is expected to contains atleast 2 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Accept only lowercase, upperacase characters")
    @Field(value = "name")
    private String name;


    @Getter
    @NotNull
    @NotEmpty
    @Size(min = 8, message = "Password should have atleast 8 characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$", message = "atleast one uppercase, one lowercase character and one number")
    @Field(value = "password")
    private String password;


    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Username should have atleast 5 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Accept only alphanumeric values")
    @Indexed(unique = true)
    @Field(value = "nickname")
    private String nickname;

@Setter
    @Field(value = "companyName")
    @Size(min = 1, max = 80, message = "Name of company cannot be longer than 80 characters")
    @NotNull
    @NotEmpty
    private String companyName;

@Getter
@Setter
    @Field(value = "typeOfProduct")
    @Enumerated(EnumType.STRING)
    private List<TypeOfProduct> typeOfProduct;

    @Field(value = "userEmail")
    @Valid
    @Getter
    private UserEmail userEmail;

    @Valid
    @Field(value = "location")
    private Location location;

    public UserBase(@NotNull @NotEmpty @Size(min = 2, message = "field Name is expected to contains atleast 2 characters") @Pattern(regexp = "^[a-zA-Z]*$", message = "Accept only lowercase, upperacase characters") String name, @NotNull @NotEmpty @Size(min = 8, message = "Password should have atleast 8 characters") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$", message = "atleast one uppercase, one lowercase character and one number") String password, @NotNull @NotEmpty @Size(min = 5, message = "Username should have atleast 5 characters") @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Accept only alphanumeric values") String nickname, @Size(min = 1, max = 80, message = "Name of company cannot be longer than 80 characters") String companyName, @Valid UserEmail userEmail, @Valid Location location) {
        this.name = name;
        this.password = passwordEncoder().encode(password);
        this.nickname = nickname;
        this.companyName = companyName;
        this.typeOfProduct = new ArrayList<>();
        this.userEmail =userEmail;
        this.location = location;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder().encode(password);
    }


    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
