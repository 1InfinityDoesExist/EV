package com.evmoter.test.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evmoter.test.model.Address;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class AddressUtil {
	private static final Logger logger = LogManager.getLogger(AddressUtil.class);
	private static String apiKey = "AIzaSyDlCI3_C-zFJeHvRaQ0JxMbm6PD5syCKak";
	private static GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
	public static HashMap parseAddress(String address) {

		logger.info("Start method:: parseAddress: Address passed : " + address);
		HashMap addrObj = new HashMap();

		String country = "";
		String state = "";
		String postCode="";

		

		try {
			GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
			if(results != null && results.length > 0)
			{
				// Getting latitude and longitude
				double latitude = results[0].geometry.location.lat;
				double longitude = results[0].geometry.location.lng;
				addrObj.put(Constants.LATITUDE, latitude);
				addrObj.put(Constants.LONGITUDE, longitude);
	
				// Getting address components
				AddressComponent[] addrComps = results[0].addressComponents;
				for (int i = 0; i < addrComps.length; i++) {
					logger.debug(addrComps[i].types[0] + " - " + addrComps[i].longName);
					if (AddressComponentType.COUNTRY.equals(addrComps[i].types[0])) {
						country = addrComps[i].longName;
						addrObj.put(Constants.COUNTRY, country);
					} else if (AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1.equals(addrComps[i].types[0])) {
						state = addrComps[i].longName;
						addrObj.put(Constants.STATE, state);
					}else if(AddressComponentType.POSTAL_CODE.equals(addrComps[i].types[0])) {
						postCode=addrComps[i].longName;
						addrObj.put(Constants.POSTCODE, postCode);
				     }else
						continue;
				}
			}

		} catch (ApiException apex) {
			logger.error("ApiException calling the geocode API" + apex.getMessage());
		} catch (InterruptedException iex) {
			logger.error("InterruptedException calling the geocode API" + iex.getMessage());
		} catch (IOException ioex) {
			logger.error("IOException calling the geocode API" + ioex.getMessage());
		}
		logger.info("End method:: parseAddress");
		return addrObj;
	}
	
	public static List<Address> populateGeocode(List<Address> addresses, String mode) {
	    System.out.println("hi...");
		List<Address> addrList = new ArrayList<Address>();
		
		if(addresses != null) {
			for(Address address: addresses) {
				if("CREATE".equals(mode) || "UPDATE".equals(mode)) {
					if(address != null) {
						String addrString = address.getAddressLine1()+" "+(address.getAddressLine2()!=null?address.getAddressLine2():"")
											+" "+address.getCity()+" "+address.getState()+" "+address.getPostCode()
											+" "+address.getCountry();
						HashMap addrObj = parseAddress(addrString);
						address.setLatitude((Double)addrObj.get(Constants.LATITUDE));
						address.setLongitude((Double)addrObj.get(Constants.LONGITUDE));
					}
					addrList.add(address);
				}
			}
		}
		
		return addrList;
	}
	

	public static Address populateGeocodeForContact(Address addresses, String mode) {
		
		
		if(addresses != null) {
			
				if("CREATE".equals(mode) || "UPDATE".equals(mode)) {
					
						String addrString = addresses.getAddressLine1()+" "+(addresses.getAddressLine2()!=null?addresses.getAddressLine2():"")
											+" "+addresses.getCity()+" "+addresses.getState()+" "+addresses.getPostCode()
											+" "+addresses.getCountry();
						HashMap addrObj = parseAddress(addrString);
						addresses.setLatitude((Double)addrObj.get(Constants.LATITUDE));
						addresses.setLongitude((Double)addrObj.get(Constants.LONGITUDE));
					}
					
				}
			
		
		
		return addresses;
	}
	
	public static String getFormattedAddress(double longitude,double latitude)
	{
		LatLng latLng = new LatLng(latitude,longitude);
		GeocodingApiRequest add = GeocodingApi.reverseGeocode(context, latLng);
		GeocodingResult result[] = add.awaitIgnoreError();
		if (result != null) {
			return result[0].formattedAddress;
		}
		else
			return null;
	}
}
