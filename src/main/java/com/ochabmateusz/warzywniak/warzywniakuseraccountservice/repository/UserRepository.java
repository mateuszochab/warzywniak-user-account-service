package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.Premium;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.TypeOfProduct;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Component
public interface UserRepository {


    User returnUserFromDb(String id) throws NotFoundException;

    User confirmUserAndReturn(User user);

    void saveUserInDb(User user);


    User changeUsername(User user,String name);

    User changePassword(User user, String password);

    User changeCompanyName(User user, String companyName);

    User addProductType(User user, TypeOfProduct productType);

    User removeProductType(User user, TypeOfProduct productType) throws Exception;

    List<TypeOfProduct> getProductTypeList(User user);

    String getActiveEmail(String id) throws Exception;

    User addNewEmail(User user, String email);

    User removeWaitingEmail(User user, String email) throws Exception;

    User confirmEmail(User user, String email) throws Exception;

    List<String> getHistoryOfUserEmails(User user);

    void activatePremium(User user) throws ParseException;

    Premium getPremiumDetails(User user) throws Exception;

    String getPremiumEndsDate(Premium premiumDetails);

    User findUserByEmail(String email) throws NotFoundException;

    List<String> getUserFriendList(User user);

    Map<String, User> sendFriendRequest(User me, User friend) throws Exception;

    Map<String, User> removeInvitationToFriend(User me, User friend) throws Exception;

    Map<String, User> quitFriendship(User me, User friend);

    Map<String, User> rejectInvitation(User me, User friend) throws Exception;

    List<String> getConversationsList(User user) throws Exception;
}
