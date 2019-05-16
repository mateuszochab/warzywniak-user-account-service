package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.service;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.*;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.UserRepository;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.WuserMongoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserRepository {


    private final WuserMongoRepository wuserMongoRepository;

    @Autowired
    public UserService(WuserMongoRepository wuserMongoRepository) {
        this.wuserMongoRepository = wuserMongoRepository;
    }

//PUBLIC METHODS

    //Find and return User from database
    @Override
    public User returnUserFromDb(String id) throws NotFoundException {
        Optional<User> user = this.wuserMongoRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    //confirms users account and returns
    @Override
    public User confirmUserAndReturn(User user) {
        user.setConfirmed(true);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.CONFIRMATION));
        return user;
    }

    //save user in database
    @Override
    public void saveUserInDb(User user) {
        this.wuserMongoRepository.save(user);

    }

    //change username and returns updated user
    @Override
    public User changeUsername(User user, String name) {
        user.getUserBase().setName(name);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.NAME));
        return user;
    }

    //change users password and returns updated user
    @Override
    public User changePassword(User user, String password) {
        user.getUserBase().setPassword(password);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.PASSWORD));
        return user;
    }

    //change company Name
    @Override
    public User changeCompanyName(User user, String companyName) {
        user.getUserBase().setCompanyName(companyName);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.COMPANY_NAME));
        return user;
    }

    //Add new product type
    @Override
    public User addProductType(User user, TypeOfProduct productType) {
        user.getUserBase().getTypeOfProduct().add(productType);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.ADD_PRODUCT_TYPE));
        return user;
    }

    //remove product type from list
    @Override
    public User removeProductType(User user, TypeOfProduct productType) throws Exception {

        List<TypeOfProduct> typeOfProductList = user.getUserBase().getTypeOfProduct();
        if (!typeOfProductList.isEmpty() && typeOfProductList.contains(productType)) {

            typeOfProductList.remove(productType);
            user.getUserBase().setTypeOfProduct(typeOfProductList);
            user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.REMOVE_PRODUCT_TYPE));

        } else {
            throw new Exception("Could not find product type on product list");
        }

        return user;
    }

    //returns products types list
    @Override
    public List<TypeOfProduct> getProductTypeList(User user) {
        return user.getUserBase().getTypeOfProduct();
    }


    //returns active email
    @Override
    public String getActiveEmail(String id) throws Exception {
        User user = returnUserFromDb(id);
        String activeEmail = user.getUserBase().getUserEmail().getActiveEmail();
        if (activeEmail.isEmpty()) {
            throw new Exception("There is no active email");
        }


        return activeEmail;
    }


    //add new email waiting for confirmation
    @Override
    public User addNewEmail(User user, String email) {
        user.getUserBase().getUserEmail().setWaitingEmail(email);
        user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.NEW_EMAIL_ADDED));
        return user;
    }

    @Override
    public User removeWaitingEmail(User user, String email) throws Exception {
        if (user.getUserBase().getUserEmail().getWaitingEmail().equals(email)) {
            user.getUserBase().getUserEmail().setWaitingEmail(email);
            user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.WAITING_EMAIL_REMOVED));
            return user;
        } else {
            throw new Exception("could not remove email -reason: waiting email and passed email to remove are not same ");
        }

    }

    @Override
    public User confirmEmail(User user, String email) throws Exception {

        if (user.getUserBase().getUserEmail().getWaitingEmail().equals(email)) {
            user.getUserBase().getUserEmail().getListOfPreviousEmails().add(getActiveEmail(user.getId()));
            user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.EMAIL_ARCHIVED));
            user.getUserBase().getUserEmail().setActiveEmail(email);
            user.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.ACTIVE_EMAIL_CHANGED));
            return user;

        } else {
            throw new Exception("could not confirm email -reason: email waiting for confirmation and email sent to be confirmed are not the same ");
        }
    }

    @Override
    public List<String> getHistoryOfUserEmails(User user) {
        return user.getUserBase().getUserEmail().getListOfPreviousEmails();
    }

    @Override
    public void activatePremium(User user) throws ParseException {

        String startDate = setDate();

        user.getPremium().setActive(true);
        user.getPremium().setStartDatePremium(startDate);
        user.getPremium().setEndDatePremium(calculateDateOfPremiumEnd(startDate, 30));
    }

    @Override
    public Premium getPremiumDetails(User user) throws Exception {
        Premium premium = user.getPremium();

        if (premium.isActive()) {
            return premium;
        } else {

            throw new Exception("User is not premium User");
        }
    }

    @Override
    public String getPremiumEndsDate(Premium premiumDetails) {
        return premiumDetails.getEndDatePremium();
    }

    @Override
    public User findUserByEmail(String email) throws NotFoundException {
        Optional<User> user = Optional.ofNullable(this.wuserMongoRepository.findOneByUserBaseUserEmailActiveEmail(email));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public List<String> getUserFriendList(User user) {

        List<Friend> friendsList = user.getListOfConnectedFriends();


        //friendsList.forEach(item->item.isConnected()?item.getIdConnectedFriend():)

        List<String> listOfFriendsIDs = friendsList.stream()
                .filter(item -> item.isConnected())
                .map(Friend::getIdConnectedFriend)
                .collect(Collectors.toList());


        return listOfFriendsIDs.isEmpty() ? null : listOfFriendsIDs;
    }

    @Override
    public Map<String, User> sendFriendRequest(User me, User friend) throws Exception {
        String friendId = friend.getId();
        String myId = friend.getId();
        if (!me.getSendInvitationToFriendList().isEmpty() && !me.getSendInvitationToFriendList().entrySet().stream()
                .map(item -> item.getKey())
                .filter(key -> key.getIdConnectedFriend().equals(friendId))
                .collect(Collectors.toList())
                .isEmpty()) {
            throw new Exception("Invitation to this user has been already sent");
        }
        if (me.getListOfConnectedFriends().stream()
                .filter(key -> key.getIdConnectedFriend().equals(friendId))
                .map(Friend::getIdConnectedFriend)
                .collect(Collectors.toList())
                .isEmpty()) {

            throw new Exception(("You are already friends"));


        }
        String date = setDate();
        me.getSendInvitationToFriendList().put(new Friend(friendId, false, null, null), date);
        friend.getRequestConnectionFriendList().put(new Friend(myId, false, null, null), date);

        return Map.of("me", me, "friend", friend);


    }

    @Override
    public Map<String, User> removeInvitationToFriend(User me, User friend) throws Exception {

        String friendId = friend.getId();
        String myId = friend.getId();
        if (!me.getSendInvitationToFriendList().isEmpty() && !me.getSendInvitationToFriendList().entrySet().stream()
                .map(item -> item.getKey())
                .filter(key -> key.getIdConnectedFriend().equals(friendId))
                .collect(Collectors.toList())
                .isEmpty()) {
            throw new Exception("Invitation unavailable");
        }
        if (friend.getRequestConnectionFriendList().entrySet().stream()
                .map(key -> key.getKey())
                .filter(key -> key.getIdConnectedFriend().equals(myId))
                .collect(Collectors.toList())
                .isEmpty()) {

            throw new Exception(("Invitation unavailable- friend probably has accepted your invitation"));
        }


        me.getSendInvitationToFriendList().remove(new Friend(friendId, false, null, null));
        friend.getRequestConnectionFriendList().remove(new Friend(myId, false, null, null));

        return Map.of("me", me, "friend", friend);

    }

    @Override
    public Map<String, User> quitFriendship(User me, User friend) {


        String friendId = friend.getId();
        String myId = me.getId();

//My profile
        //get friend from ListOfConnectedFriend
        Friend meToNoFriendsAnyLongerList = me.getListOfConnectedFriends()
                .stream()
                .filter(item -> item.getIdConnectedFriend().equals(friendId))
                .findAny()
                .get();
        //setting connection to false and ending date
        meToNoFriendsAnyLongerList.setConnected(false);
        meToNoFriendsAnyLongerList.setStopBeingFriendDate(setDate());

        //add friend to list of former friends
        me.getNotFriendAnyLongerList().add(meToNoFriendsAnyLongerList);

        //remove friend from list of active friends
        me.getListOfConnectedFriends().removeIf(item -> item.getIdConnectedFriend().equals(friendId));

        //add modification type to list
        me.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.FRIENDSHIP_CANCELED));


//Friend profile

        //get friend from ListOfConnectedFriend
        Friend friendToNoFriendsAnyLongerList = me.getListOfConnectedFriends()
                .stream()
                .filter(item -> item.getIdConnectedFriend().equals(myId))
                .findAny()
                .get();

        //setting connection to false and ending date
        friendToNoFriendsAnyLongerList.setConnected(false);
        friendToNoFriendsAnyLongerList.setStopBeingFriendDate(setDate());

        //add friend to list of former friends
        friend.getNotFriendAnyLongerList().add(friendToNoFriendsAnyLongerList);

        //remove friend from list of active friends
        friend.getListOfConnectedFriends().removeIf(item -> item.getIdConnectedFriend().equals(myId));

        //add modification type to list
        friend.getModifiedDates().add(new ModifiedDate(setDate(), ModificationType.FRIENDSHIP_CANCELED));


        return Map.of("me", me, "friend", friend);
    }

    @Override
    public Map<String, User> rejectInvitation(User me, User friend) throws Exception {

        String friendId = friend.getId();
        String myId = me.getId();

        List<Friend> meRequestConnectionFriendList = me.getRequestConnectionFriendList().entrySet().stream()
                .map(item -> item.getKey())
                .filter(key -> key.getIdConnectedFriend().equals(friendId))
                .collect(Collectors.toList());

        List<Friend> friendSendInvitationToFriendList = friend.getSendInvitationToFriendList().entrySet().stream()
                .map(item -> item.getKey())
                .filter(key -> key.getIdConnectedFriend().equals(myId))
                .collect(Collectors.toList());

        if (!me.getRequestConnectionFriendList().isEmpty() && !meRequestConnectionFriendList.isEmpty()) {
            throw new Exception("Invitation unavailable");
        }
        if (!friend.getSendInvitationToFriendList().isEmpty() && !friendSendInvitationToFriendList.isEmpty()) {
            throw new Exception("Invitation unavailable");
        }


        me.getRequestConnectionFriendList().remove(meRequestConnectionFriendList);

        friend.getSendInvitationToFriendList().remove(friendSendInvitationToFriendList);


        return Map.of("me", me, "friend", friend);


    }

    @Override
    public Set<String> getConversationsList(User user) throws Exception {


        Set<String> conversationsList = user.getConversations();

        if (conversationsList.isEmpty()) {

            throw new Exception("User does not run any conversation");
        }


        return conversationsList;
    }

    @Override
    public User addConversation(User user, String conversationId) {

        user.getConversations().add(conversationId);
        return user;
    }

    @Override
    public User leaveConversation(User user, String conversationId) {
        user.getAbandonedConversations().add(user.getConversations().stream()
                .filter(item -> item.equals(conversationId))
                .findAny()
                .get());

        user.getConversations().remove(conversationId);
        return user;

    }

    @Override
    public List<String> getAbandonedConversationsList(User user) throws Exception {
        List<String> conversationsList = user.getAbandonedConversations();

        if (conversationsList.isEmpty()) {

            throw new Exception("Abandoned Conversation List is empty");
        }


        return conversationsList;
    }


//PRIVATE METHODS

    //returns present date in "yyyy-MM-dd HH:mm:ss" format
    private String setDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }


    //calculate future date based on starts date
    private String calculateDateOfPremiumEnd(String startDate, int daysInFuture) throws ParseException {
        //new Calendar object
        Calendar c;

        //parse string startDate to Date object with given pattern
        Date start = stringDateToDateObject(startDate);

        //startDate object of type Date to Calendar object
        c = dateObjectToCalendarObject(start);
        //sets new date 30 days in future
        c.add(Calendar.DATE, daysInFuture);

        //Calendar object with ending date to object Date
        Date endDate = calendarObjectToDateObject(c);


        //add pattern and transform  to Date to String
        String pattern = "yyyy-MM-dd HH:mm:ss";
        dateObjectToString(endDate, pattern);


        return dateObjectToString(endDate, pattern);
    }

    private Date stringDateToDateObject(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

    }

    //transform date object into Calendar Object
    private Calendar dateObjectToCalendarObject(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }

    //transform calendar object to Date
    private Date calendarObjectToDateObject(Calendar calendar) {
        Date date = calendar.getTime();

        return date;
    }

    private String dateObjectToString(Date date, String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


        return simpleDateFormat.format(date);
    }


}
