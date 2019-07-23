package com.evmoter.test.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evmoter.test.model.Address;
import com.evmoter.test.model.UserRegistrationPage;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistrationPage, Long> {

    @Query("select UserRegistrationPage from #{#entityName} UserRegistrationPage where deleteFlag=false")
    public List<UserRegistrationPage> getAllRegisteredUser();

    @Modifying
    @Transactional
    @Query("update UserRegistrationPage u set u.deleteFlag=true where u.mobile=?1")
    public void softDelete(String phoneNumber);

    @Query("select UserRegistrationPage from #{#entityName} UserRegistrationPage where mobile=?1")
    public UserRegistrationPage getRegisteredUserByPhoneNumber(String phoneNumber);

    @Query("select UserRegistrationPage from #{#entityName} UserRegistrationPage where email=?1 and mobile=?2")
    public UserRegistrationPage getUserByEmailAndPhone(String email, String phone);

    @Modifying
    @Transactional
    @Query("update UserRegistrationPage u set u.deleteFlag=true where u.address=?1")
    public void updateAddress(List<Address> addrList);

}
