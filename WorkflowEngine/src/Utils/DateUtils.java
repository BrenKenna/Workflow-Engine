/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author kenna
 */
public class DateUtils {

    	
	// Standardize date format for the app
	private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final int EXPIRATION_DAYS = 2; // Constant to set expiration date of tokens
	
	
	/**
	 * Get current date
	 * 
	 * @return Date - dd/MM/yyyy
	 */
	public Date getNow() {
		return new Date((new Date()).getTime());
	}
	
	
	/**
	 * Return current date as string
	 * 
	 * @return String - dd/MM/yyyy
	 */
	public String getNowString() {
		return dateFormat.format(getNow());
	}

	
	/**
	 * Format input date to string
	 * 
	 * @param date
	 * @return String - dd/MM/yyyy
	 */
	public String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	
	/**
	 * Parse input date string
	 * 
	 * @param date - dd/MM/yyyy
	 * @return Date
	 */
	public Date parseDate(String date) {
		
		// Try format input date string
		Date output = null;
		try {
			output = dateFormat.parse(date);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return result
		// Null handled externally
		return output;
	}
	
	
	/**
	 * Get an expiration date for input date
	 * 
	 * @param queryDate
	 * @return Date
	 */
	public Date getExpireDate(Date inputDate) {
		
		// Set calendar to input
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate); 
        
        // Add the expiration time
        calendar.add(Calendar.DAY_OF_YEAR, EXPIRATION_DAYS);
        return calendar.getTime();
	}
	
	
	/**
	 * Return an expirary date from input string
	 * 
	 * @param inputDate
	 * @return String - dd/MM/yyyy
	 */
	public String getExpireString(String inputDate) {
		Date date = parseDate(inputDate);
		Date expireDate = getExpireDate(date);
		return formatDate(expireDate);
	}
	
	/**
	 * Return whether input date is before now
	 * 
	 * @param inputDate
	 * @return boolean
	 */
	public boolean hasExceeded(Date inputDate) {
		return getNow().getTime() > inputDate.getTime();
	}
	
	
	/**
	 * Validate whether queried date has expired
	 * 
	 * @param queryDate
	 * @return boolean
	 */
	public boolean isExpired(String queryDate) {
		Date inputDate = parseDate(queryDate);
		if (inputDate != null) {
			return hasExceeded(inputDate);
			
		}
		else {
			return false; // Should really be an exception
		}
	}
}
