package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.Premium;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.Role;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.User;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.UserBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {


    @JsonIgnore
    private String id;

    @JsonProperty("userBase")
    private UserBase userBase;

    @JsonProperty("confirmed")
    private boolean confirmed;

    @JsonProperty("superUser")
    private boolean superUser;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("premium")
    private Premium premium;

    @JsonProperty("createdDate")
    private String createdDate;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userBase = user.getUserBase();
        this.confirmed = user.isConfirmed();
        this.superUser = user.isSuperUser();
        this.role = user.getRole();
        this.premium = user.getPremium();
        this.createdDate = user.getCreatedDate();
    }
}
