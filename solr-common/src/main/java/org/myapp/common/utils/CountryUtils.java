package org.myapp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryUtils {

	private static Map<String, Country> countryMap = null;

	public static Map<String, Country> getCountryMap() {
		if (countryMap == null) {
			synchronized (CountryUtils.class) {
				if (countryMap == null) {
					
					countryMap = new HashMap<String, Country>();

					ClassLoader loader = Thread.currentThread().getContextClassLoader();
					InputStream input = loader.getResourceAsStream("country.json");
					ObjectMapper mapper = new ObjectMapper();
					try {
						Country[] countrys  = mapper.readValue(input, Country[].class);
						if(countrys != null && countrys.length > 0) {
							for (Country country : countrys) {
								countryMap.put(country.getCountrycode(), country);
							}
						}
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							input.close();
							input = null;
							mapper = null;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		}

		return countryMap;
	}

	public static String getChineseCountry(String code) {

		if (getCountryMap().get(code) != null) {
			return getCountryMap().get(code).getCountryName();
		}

		return code;
	}

	public static String getEnCountry(String code) {

		if (getCountryMap().get(code) != null) {
			return getCountryMap().get(code).getEn_name();
		}

		return code;
	}
	
	public static void main(String[] args) {
		System.out.println(getChineseCountry("AD"));
		
	}
	


}
