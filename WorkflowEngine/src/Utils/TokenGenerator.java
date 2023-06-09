/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 *
 * @author kenna
 */
public class TokenGenerator {
	
	/**
	 * Generate a salt from a random UUID
	 * 
	 * @return String
	 */
	public String generateSalt() {
		return UUID.randomUUID().toString();
	}
	
	
	/**
	 * Generate a hexadecimal session key from a random UUID
	 * @return String
	 */
	public String generateToken() {
		String str = generateSalt();
		return getHexString( str.getBytes() );
	}
	
	/**
	 * Get hexadecimal string from byte array
	 * 
	 * @param digest
	 * @return String
	 */
	private String getHexString_Alt(byte[] digest) {
		
		// Convert byte array to hexadecimal string
		StringBuffer hex = new StringBuffer();
        for (int i = 0; i< digest.length; i++) {
        	hex.append(Integer.toHexString( digest[i]));
        }
		
		// Update output
		return hex.toString();
	}
	
	
	/**
	 * Default method for returning hexadecimal string from hashed password. 
	 * Looks better than alternate
	 * 
	 * @param digest
	 * @return Hexidecimal string from a byte array
	 */
	private String getHexString(byte[] digest) {
		StringBuilder hexString = new StringBuilder(2 * digest.length);
	    for (int i = 0; i < digest.length; i++) {
	        String hex = Integer.toHexString(0xff & digest[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	/**
	 * Return sha-256 hash of salted password
	 * 
	 * @param password
	 * @param salt
	 * @return String - hash of salted password
	 */
	public String generateSHA_Hash(String password, String salt) {
		
		// Try generate hash from password
		String output = null;
		try {
			
			// Generate digest for salted password
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			String toHash = password + "-" + salt;
			messageDigest.update(toHash.getBytes(StandardCharsets.UTF_8));
			
			
			// Parse digest to hexadecimal string
			byte[] digest = messageDigest.digest();
			output = getHexString(digest);
			messageDigest.reset();
		}
		
		// Catch exception
		catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		
		// Return results
		// Null string & proper output should be handled appropriately
		return output;
	}
	
	
	/**
	 * Return message digest for hashing passwords or null. 
	 * Idea is that the most modern version can be used/changed 
	 * without changing whatever class using it
	 * 
	 * @param hashType
	 * @return MessageDigest
	 * @throws NoSuchAlgorithmException 
	 */
	private MessageDigest getDigest(String hashType) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = null;
		messageDigest = MessageDigest.getInstance(hashType);
		return messageDigest;
	}
	
	
	/**
	 * Generate hashed password from specified hash type. 
	 * Null output for invalid hash types
	 * 
	 * @param hashType
	 * @param password
	 * @param salt
	 * @return String - hash of salted password
	 */
	public String getHash(String hashType, String password, String salt) {
		String output = null;
		try {
			
			// Generate digest from salted password
			MessageDigest messageDigest = getDigest(hashType);
			String toHash = password + "-" + salt;
			messageDigest.update(toHash.getBytes(StandardCharsets.UTF_8));
			
			
			// Parse digest to hexadecimal string
			byte[] digest = messageDigest.digest();
			output = getHexString(digest);
			messageDigest.reset();
		}
		
		catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		
		// Return result
		// Null values should be handled
		return output;
	}
}
