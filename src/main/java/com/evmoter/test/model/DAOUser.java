package com.evmoter.test.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.evmoter.test.util.StringEncryptDecryptConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "dao_user")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "UserName Can't Be Blank")
    @Column(name = "user_name", unique = true, nullable = false, updatable = true)
    private String username;
    @NotBlank(message = "Password Field Cannot Be Blank")
    @Column(name = "password", nullable = false, updatable = true)
    @JsonIgnore
    private String password;
    @Column(name = "email", unique = true, updatable = true, nullable = false)
    @ApiModelProperty(notes = "Email of the user")
    @Convert(converter = StringEncryptDecryptConverter.class)
    private String email;
    @Column(name = "mobile_phone", nullable = false, updatable = true, unique = true)
    @ApiModelProperty(notes = "Mobile phone number of the user")
    @Convert(converter = StringEncryptDecryptConverter.class)
    private String mobilePhone;
    @Column(name = "tag")
    private String tag;

    public DAOUser() {
    }

    public DAOUser(Long id, @NotBlank(message = "UserName Can't Be Blank") String username,
	    @NotBlank(message = "Password Field Cannot Be Blank") String password, String email, String mobilePhone,
	    String tag) {
	super();
	this.id = id;
	this.username = username;
	this.password = password;
	this.email = email;
	this.mobilePhone = mobilePhone;
	this.tag = tag;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((tag == null) ? 0 : tag.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DAOUser other = (DAOUser) obj;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (mobilePhone == null) {
	    if (other.mobilePhone != null)
		return false;
	} else if (!mobilePhone.equals(other.mobilePhone))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (tag == null) {
	    if (other.tag != null)
		return false;
	} else if (!tag.equals(other.tag))
	    return false;
	if (username == null) {
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "DAOUser [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
		+ ", mobilePhone=" + mobilePhone + ", tag=" + tag + "]";
    }

}