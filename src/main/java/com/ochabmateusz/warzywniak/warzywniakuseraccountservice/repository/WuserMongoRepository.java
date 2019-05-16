package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository;

import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WuserMongoRepository extends MongoRepository<User, String> {

    User findOneByUserBaseUserEmailActiveEmail(String email);

}
