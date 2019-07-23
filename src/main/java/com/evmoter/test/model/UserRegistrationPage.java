package com.evmoter.test.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.evmoter.test.model.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import io.swagger.annotations.ApiModelProperty;

@Entity(name = "UserRegistrationPage")
@Table(name = "user_registration_page")
@TypeDefs({ @TypeDef(name = "AddressType", typeClass = AddressType.class), })
public class UserRegistrationPage extends BaseEntity implements Serializable {
    @Column(name = "status")
    @ApiModelProperty(notes = "User First Name")
    private String status;
    @Column(name = "first_name")
    @ApiModelProperty(notes = "FirstName")
    private String firstName;
    @Column(name = "last_name")
    @ApiModelProperty(notes = "Last Name Of user")
    private String lastName;
    @Column(name = "email")
    @ApiModelProperty(notes = "Email Id Of User")
    private String email;
    @Column(name = "mobile")
    @ApiModelProperty(notes = "Mobile Number of Person")
    private String mobile;
    @Column(name = "image")
    @ApiModelProperty(notes = "Image of Person")
    private String image;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "dob")
    @ApiModelProperty(notes = "Date Of Birth of User")
    private Date dob;
    @Column(name = "referral_code")
    @ApiModelProperty(notes = "ReferralCode")
    private String referralCode;
    @Column(name = "password")
    @ApiModelProperty(notes = "password")
    private String password;

    @Column
    @Type(type = "com.evmoter.test.model.AddressType", parameters = {
	    @org.hibernate.annotations.Parameter(name = "type", value = "LIST"),
	    @org.hibernate.annotations.Parameter(name = "element", value = "com.evmoter.test.model.Address") })
    @ApiModelProperty(notes = "Address of the contact")
    private List<Address> address;

    /*
     * @Column(name = "address", columnDefinition = "jsonb")
     * 
     * @Type(type = "AddressType")
     * 
     * @ApiModelProperty(notes =
     * "Address of the Bank which contains state zipcode and others") private
     * Address address;
     */
    public UserRegistrationPage() {
	super();
	// TODO Auto-generated constructor stub
    }

    public UserRegistrationPage(String status, String firstName, String lastName, String email, String mobile,
	    String image, Date dob, String referralCode, String password,
	    List<com.evmoter.test.model.Address> address) {
	super();
	this.status = status;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.mobile = mobile;
	this.image = image;
	this.dob = dob;
	this.referralCode = referralCode;
	this.password = password;
	this.address = address;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getMobile() {
	return mobile;
    }

    public void setMobile(String mobile) {
	this.mobile = mobile;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public Date getDob() {
	return dob;
    }

    public void setDob(Date dob) {
	this.dob = dob;
    }

    public String getReferralCode() {
	return referralCode;
    }

    public void setReferralCode(String referralCode) {
	this.referralCode = referralCode;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public List<Address> getAddress() {
	return address;
    }

    public void setAddress(List<Address> address) {
	this.address = address;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((dob == null) ? 0 : dob.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((image == null) ? 0 : image.hashCode());
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((referralCode == null) ? 0 : referralCode.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	UserRegistrationPage other = (UserRegistrationPage) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (dob == null) {
	    if (other.dob != null)
		return false;
	} else if (!dob.equals(other.dob))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (image == null) {
	    if (other.image != null)
		return false;
	} else if (!image.equals(other.image))
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (mobile == null) {
	    if (other.mobile != null)
		return false;
	} else if (!mobile.equals(other.mobile))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (referralCode == null) {
	    if (other.referralCode != null)
		return false;
	} else if (!referralCode.equals(other.referralCode))
	    return false;
	if (status == null) {
	    if (other.status != null)
		return false;
	} else if (!status.equals(other.status))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "UserRegistrationPage [status=" + status + ", firstName=" + firstName + ", lastName=" + lastName
		+ ", email=" + email + ", mobile=" + mobile + ", image=" + image + ", dob=" + dob + ", referralCode="
		+ referralCode + ", password=" + password + ", address=" + address + "]";
    }

}
