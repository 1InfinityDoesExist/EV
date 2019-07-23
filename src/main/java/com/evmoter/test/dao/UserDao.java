package com.evmoter.test.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evmoter.test.model.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {
    // @Query(nativeQuery = true, value = "select dao_user from dao_user where
    // username=?1")
    public DAOUser findByUsername(String username);

    @Query("Select DAOUser from #{#entityName} DAOUser where email=?1 and mobilePhone=?2")
    public DAOUser findByEmailAndMoblie(String email, String mobilePhone);

}