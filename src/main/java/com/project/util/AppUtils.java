package com.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;

import org.joda.time.DateTime;

public class AppUtils {
	
	public Integer generateIdValue(Integer staticValue)
	{
		DateTime dt = new DateTime();
		Integer year = dt.getYear();
		Integer month = dt.getMonthOfYear();
		Integer day = dt.getDayOfYear();
		Integer hour = dt.getHourOfDay();
		Integer milli = dt.getMillisOfDay();

		Integer id = staticValue + year + month + day + hour + milli;
		
		return id;		
	}
	
/*******************************************************************************/

	//need to change this random number logic - logic using current timestamp
		public int getRandomInteger(){
			Random random = new Random();
			return random.nextInt(Integer.MAX_VALUE)  + 1 ;
		}
		
		
/*******************************************************************************/

		public String[] covertObjectArrayToStringArray(ArrayList<String> input){
			String output[] = new String[input.size()];
			if(input != null){
				for(int i = 0; i < input.size(); i++){
					output[i] = input.get(i);
				}
			}
			return output;
		}
		
/*******************************************************************************/
		
		public Map<String,String> getCookiesValue(Cookie[] cookies){
			Map<String,String> coockieMap = new HashMap<String, String>();
			if(cookies != null){
				for(Cookie cookie : cookies){
					coockieMap.put(cookie.getName(),cookie.getValue());
				}
			}
			return coockieMap;
		}
		
		
/*******************************************************************************/
	
		public String passwordEncrypter(String password)
		{
			String generatedDigest = null;
			
			try{
				MessageDigest md = MessageDigest.getInstance("MD5");

				md.update(password.getBytes());

				byte[] bytes = md.digest();

				StringBuilder sb = new StringBuilder();
				for(int i=0; i< bytes.length ;i++)
				{
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}

				generatedDigest = sb.toString();
			}catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			
			return generatedDigest;
		}
		
/*******************************************************************************/
		
		public List<String> arraySplitter(String list)
		{
				
			if(list == null) {return null;}
			
			List<String> arrayList = new ArrayList<String>();
						
			arrayList = Arrays.asList(list.split("\\s*,\\s*"));			
			
			return arrayList; 
		}

}
