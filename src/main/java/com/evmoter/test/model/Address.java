package com.evmoter.test.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Address implements Serializable {
    private static final long serialVersionUID = -9108950901351710107L;

    @ApiModelProperty(notes = "Address Line1 of the contact")
    private String addressLine1;
    @ApiModelProperty(notes = "Address Line2 of the contact")
    private String addressLine2;
    @ApiModelProperty(notes = "City of the contact")
    private String city;
    @ApiModelProperty(notes = "State of the contact")
    private String state;
    @ApiModelProperty(notes = "Country of the contact")
    private String country;
    @ApiModelProperty(notes = "Post Code of the contact")
    private String postCode;
    @ApiModelProperty(notes = "Longitude value of the of the contact Address")
    private Double longitude;
    @ApiModelProperty(notes = "Latitude value of the of the contact Address")
    private Double latitude;
    @ApiModelProperty(notes = "Type of Address of the Contact")
    private String type;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getAddressLine1() {
	return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
	return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getPostCode() {
	return postCode;
    }

    public void setPostCode(String postCode) {
	this.postCode = postCode;
    }

    public Double getLongitude() {
	return longitude;
    }

    public void setLongitude(Double longitude) {
	this.longitude = longitude;
    }

    public Double getLatitude() {
	return latitude;
    }

    public void setLatitude(Double latitude) {
	this.latitude = latitude;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((addressLine1 == null) ? 0 : addressLine1.hashCode());
	result = prime * result + ((addressLine2 == null) ? 0 : addressLine2.hashCode());
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((country == null) ? 0 : country.hashCode());
	result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
	result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
	result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
	result = prime * result + ((state == null) ? 0 : state.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
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
	Address other = (Address) obj;
	if (addressLine1 == null) {
	    if (other.addressLine1 != null)
		return false;
	} else if (!addressLine1.equals(other.addressLine1))
	    return false;
	if (addressLine2 == null) {
	    if (other.addressLine2 != null)
		return false;
	} else if (!addressLine2.equals(other.addressLine2))
	    return false;
	if (city == null) {
	    if (other.city != null)
		return false;
	} else if (!city.equals(other.city))
	    return false;
	if (country == null) {
	    if (other.country != null)
		return false;
	} else if (!country.equals(other.country))
	    return false;
	if (latitude == null) {
	    if (other.latitude != null)
		return false;
	} else if (!latitude.equals(other.latitude))
	    return false;
	if (longitude == null) {
	    if (other.longitude != null)
		return false;
	} else if (!longitude.equals(other.longitude))
	    return false;
	if (postCode == null) {
	    if (other.postCode != null)
		return false;
	} else if (!postCode.equals(other.postCode))
	    return false;
	if (state == null) {
	    if (other.state != null)
		return false;
	} else if (!state.equals(other.state))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
		+ ", state=" + state + ", country=" + country + ", postCode=" + postCode + ", longitude=" + longitude
		+ ", latitude=" + latitude + ", type=" + type + "]";
    }
}