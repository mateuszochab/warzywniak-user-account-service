package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.controller;


import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.Premium;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.TypeOfProduct;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.User;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.UserRepository;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.ValidationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserAccountController {


    private static final String ACCEPT_JSON = "Accept=application/json";


    private final UserRepository userRepository;
    private final ValidationRepository validationRepository;

    @Autowired
    public UserAccountController(UserRepository userRepository, ValidationRepository validationRepository) {
        this.userRepository = userRepository;
        this.validationRepository = validationRepository;
    }


    //TODO take care of confirmation code when service storing confirmation code with be available
    @PatchMapping(value = "/confirmUser", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "User has been successfully confirmed")
    public void confirmUser(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {
//Fields
        String id;
        String confirmationCode;

//check out if data from request are valid
        if (requestBody.containsKey("id") && requestBody.containsKey("confirmationCode")) {
            id = (String) requestBody.get("id");
            confirmationCode = (String) requestBody.get("confirmationCode");
        } else {
            throw new NoSuchFieldException("request does not contains required fields");
        }
//find user in Database
        User user = this.userRepository.returnUserFromDb(id);
        //confirm and save user in DB
        if (!user.isConfirmed()) {
            this.userRepository.saveUserInDb(this.userRepository.confirmUserAndReturn(user));
        }
    }


    @PatchMapping(value = "/changeName", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Username has been succesfully changed")
    public void changeName(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        String id;
        String name;

        //check out if data from request are valid
        if (requestBody.containsKey("id") && requestBody.containsKey("name")) {
            id = (String) requestBody.get("id");
            name = (String) requestBody.get("name");
        } else {
            throw new NoSuchFieldException("request does not contains required fields");
        }

        User user = this.userRepository.returnUserFromDb(id);


        this.userRepository.saveUserInDb(this.userRepository.changeUsername(user, name));
    }


    @PatchMapping(value = "/changePassword", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "password has been succesfully changed")
    public void changePassword(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        String id, password;

        if (this.validationRepository.twoValidItemsInRequest("id", "password", requestBody)) {
            id = (String) requestBody.get("id");
            password = (String) requestBody.get("password");

            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.saveUserInDb(this.userRepository.changePassword(user, password));
        }
    }

    @PatchMapping(value = "/changeCompanyName", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Company name has been successfully changed")
    public void changeCompanyName(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        String id, companyName;

        if (this.validationRepository.twoValidItemsInRequest("id", "companyName", requestBody)) {

            id = (String) requestBody.get("id");
            companyName = (String) requestBody.get("companyName");

            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.saveUserInDb(this.userRepository.changeCompanyName(user, companyName));

        }
    }

    @PostMapping(value = "/addProductType", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "product type has been successfully added")
    public void addProductType(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {
        String id;
        TypeOfProduct productType;

        if (this.validationRepository.twoValidItemsInRequest("id", "productType", requestBody)) {
            id = (String) requestBody.get("id");
            productType = (TypeOfProduct) requestBody.get("productType");

            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.saveUserInDb(this.userRepository.addProductType(user, productType));
        }
    }

    @GetMapping(value = "/getProductTypes", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "product types found and returned")
    public List<TypeOfProduct> getProductTypes(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        String id;

        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {
            id = (String) requestBody.get("id");

            return this.userRepository.getProductTypeList(this.userRepository.returnUserFromDb(id));
        }

        return null;
    }


    @DeleteMapping(value = "/removeProductFromList", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "product type successfully found and removed")
    public void removeProductFromList(@RequestBody Map<String, Object> requestBody) throws Exception {

        String id;
        TypeOfProduct productType;
        if (this.validationRepository.twoValidItemsInRequest("id", "productType", requestBody)) {
            id = (String) requestBody.get("id");
            productType = (TypeOfProduct) requestBody.get("productType");

            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.saveUserInDb(this.userRepository.removeProductType(user, productType));
        }

    }


    @GetMapping(value = "/getActiveEmail", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Active email returned")
    public String getActiveEmail(@RequestBody Map<String, Object> requestBody) throws Exception {

        String id;

        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {

            id = (String) requestBody.get("id");

            return this.userRepository.getActiveEmail(id);
        }


        return null;
    }


    @PostMapping(value = "/addNewEmail", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "new email has been added")
    public void addNewEmail(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        String id, email;

        if (this.validationRepository.twoValidItemsInRequest("id", "email", requestBody)) {
            id = (String) requestBody.get("id");
            email = (String) requestBody.get("email");

            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.saveUserInDb(this.userRepository.addNewEmail(user, email));
            //TODO call external confirmational service sending user id, new email which will call
            //TODO email sender service
        }
    }


    @DeleteMapping(value = "/removeNotConfirmedEmail", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "not confirmed email has beed removed")
    public void removeNotConfirmedEmail(@RequestBody Map<String, Object> requestBody) throws Exception {

        String id, email;

        if (this.validationRepository.twoValidItemsInRequest("id", "email", requestBody)) {
            id = (String) requestBody.get("id");
            email = (String) requestBody.get("email");

            User user = this.userRepository.returnUserFromDb(id);
            user = this.userRepository.removeWaitingEmail(user, email);
            this.userRepository.saveUserInDb(user);
        }
    }


    @GetMapping(value = "/confirmEmail", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Email has been successfully confirmed")
    public void confirmEmail(@RequestBody Map<String, Object> requestBody) throws Exception {

        String id, email;

        if (this.validationRepository.twoValidItemsInRequest("id", "email", requestBody)) {
            id = (String) requestBody.get("id");
            email = (String) requestBody.get("email");

            User user = this.userRepository.returnUserFromDb(id);

            user = this.userRepository.confirmEmail(user, email);
            this.userRepository.saveUserInDb(user);
        }


    }

    @GetMapping(value = "/getHistoryOfEmails", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "User Emails has been successfully returned")
    public List<String> getHistoryOfEmails(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {


        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {

            String id = (String) requestBody.get("id");

            User user = this.userRepository.returnUserFromDb(id);
            return this.userRepository.getHistoryOfUserEmails(user);
        }


        return null;
    }

    @GetMapping(value = "/activatePremium", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Premium is activated")
    public void activatePremium(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException, ParseException {


        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {
            String id = (String) requestBody.get("id");
            User user = this.userRepository.returnUserFromDb(id);
            this.userRepository.activatePremium(user);
        }
    }


    @GetMapping(value = "getPremiumDetails", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Premium details has been returned")
    public Premium getPremiumDetails(@RequestBody Map<String, Object> requestBody) throws Exception {


        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {


            String id = (String) requestBody.get("id");
            User user = this.userRepository.returnUserFromDb(id);

            return this.userRepository.getPremiumDetails(user);
        }


        return null;
    }


    @GetMapping(value = "/getPremiumEndsTime", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Premium end date successfully returned")
    public String getPremiumEndsTime(Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {


            String id = (String) requestBody.get("id");


            return this.userRepository.getPremiumEndsDate(this.userRepository.getPremiumDetails(
                    this.userRepository.returnUserFromDb(id))
            );
        }

        return null;

    }


    @GetMapping(value = "/findUserByEmail", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "User found by email")
    public User findUserByEmail(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {


        if (this.validationRepository.oneValidItemsInRequest("email", requestBody)) {

            String email = (String) requestBody.get("email");

            return this.userRepository.findUserByEmail(email);
        }

        return null;
    }

    @GetMapping(value = "/getUserFriendList", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Users friend list successfully returned")
    public List<String> getUserFriendList(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {


        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {


            String id = (String) requestBody.get("id");


            return this.userRepository.getUserFriendList(this.userRepository.returnUserFromDb(id));
        }

        return null;
    }

    public void confirmFriendRequest(@RequestBody Map<String, Object> requestBody) {


    }

    @PostMapping(value = "/sendFriendRequest", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Request of being friend has been successfully sent")
    public void sendFriendRequest(@RequestBody Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.twoValidItemsInRequest("Myid", "requestFriendId", requestBody)) {

            String Myid = (String) requestBody.get("Myid");
            String requestFriendId = (String) requestBody.get("requestFriendId");

            User me = this.userRepository.returnUserFromDb(Myid);
            User friend = this.userRepository.returnUserFromDb(requestFriendId);


            Map<String, User> map = this.userRepository.sendFriendRequest(me, friend);
            this.userRepository.saveUserInDb(map.get("me"));
            this.userRepository.saveUserInDb(map.get("friend"));


        }
    }

    @PostMapping(value = "/removeInvitationToFriend", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Invitation successfully canceled")
    public void removeInvitationToFriend(@RequestBody Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.twoValidItemsInRequest("Myid", "requestFriendId", requestBody)) {

            String Myid = (String) requestBody.get("Myid");
            String requestFriendId = (String) requestBody.get("requestFriendId");

            User me = this.userRepository.returnUserFromDb(Myid);
            User friend = this.userRepository.returnUserFromDb(requestFriendId);

            Map<String, User> map = this.userRepository.removeInvitationToFriend(me, friend);
            this.userRepository.saveUserInDb(map.get("me"));
            this.userRepository.saveUserInDb(map.get("friend"));


        }
    }


    @PostMapping(value = "/quitFriendship", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "friendship has been ended successfully :)")
    public void quitFriendship(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {


        if (this.validationRepository.twoValidItemsInRequest("Myid", "requestFriendId", requestBody)) {

            String Myid = (String) requestBody.get("Myid");
            String requestFriendId = (String) requestBody.get("requestFriendId");


            User me = this.userRepository.returnUserFromDb(Myid);
            User friend = this.userRepository.returnUserFromDb(requestFriendId);

            Map<String, User> map = this.userRepository.quitFriendship(me, friend);
            this.userRepository.saveUserInDb(map.get("me"));
            this.userRepository.saveUserInDb(map.get("friend"));

        }
    }

    @PostMapping(value = "/rejectInvitation", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "invitation rejected")
    public void rejectInvitation(@RequestBody Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.twoValidItemsInRequest("Myid", "requestFriendId", requestBody)) {

            String Myid = (String) requestBody.get("Myid");
            String requestFriendId = (String) requestBody.get("requestFriendId");

            User me = this.userRepository.returnUserFromDb(Myid);
            User friend = this.userRepository.returnUserFromDb(requestFriendId);

            Map<String, User> map = this.userRepository.rejectInvitation(me, friend);
            this.userRepository.saveUserInDb(map.get("me"));
            this.userRepository.saveUserInDb(map.get("friend"));


        }
    }

    @GetMapping(value = "/getConversationsList", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Conversation List Returned")
    public Set<String> getConversationsList(@RequestBody Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {


            String id = (String) requestBody.get("id");


            User user = this.userRepository.returnUserFromDb(id);
            return this.userRepository.getConversationsList(user);
        }


        return null;
    }

    @GetMapping(value = "/addConversation", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "Conversation has been added")
    public void addConversation(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        if (this.validationRepository.twoValidItemsInRequest("id", "conversationId", requestBody)) {

            String id = (String) requestBody.get("id");
            String conversationId = (String) requestBody.get("conversationId");

            User user = this.userRepository.returnUserFromDb(id);

            this.userRepository.saveUserInDb(this.userRepository.addConversation(user, conversationId));

        }
    }

    @GetMapping(value = "/leaveConversation", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "User Left Conversation")
    public void leaveConversation(@RequestBody Map<String, Object> requestBody) throws NoSuchFieldException, NotFoundException {

        if (this.validationRepository.twoValidItemsInRequest("id", "conversationId", requestBody)) {

            String id = (String) requestBody.get("id");
            String conversationId = (String) requestBody.get("conversationId");

            User user = this.userRepository.returnUserFromDb(id);

            this.userRepository.saveUserInDb(this.userRepository.leaveConversation(user, conversationId));

        }
    }

    @GetMapping(value = "/getAbandonedConversationsList", headers = ACCEPT_JSON)
    @ResponseStatus(value = HttpStatus.OK, reason = "User Left Conversation")
    public List<String> getAbandonedConversationsList(@RequestBody Map<String, Object> requestBody) throws Exception {

        if (this.validationRepository.oneValidItemsInRequest("id", requestBody)) {


            String id = (String) requestBody.get("id");


            User user = this.userRepository.returnUserFromDb(id);
            return this.userRepository.getAbandonedConversationsList(user);
        }
        return null;
    }
}
