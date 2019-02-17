package hr.fer.zemris.java.hw15.web.servlets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Util class that contains methods for calculaing hash values of passwords.
 * Passwords cannot be stored in database in plain text, so methods in this class are used 
 * to encrypt them. Algorithm used is SHA-1.
 * 
 * @author Alex
 *
 */
public class Util {
	
	/**
	 * Checks if given digest equals calculated digest. Algorithm used is sha-1.
	 * @param password
	 * 				password to be checked
	 * @param expected
	 * 				expected value of password hash
	 * @return 
	 * 				true if calculated and expected match, false otherwise
	 * @throws NoSuchAlgorithmException
	 */
	public static String getsha(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		String digest = bytetohex(md.digest(password.getBytes())).toLowerCase();
		return digest;
	}
	
	/**
	 * Conversion of bytes to text.
	 * @param bytearray 
	 * 				bytes to be converted to text.
	 * @return 
	 * 				text representation of bytearray.
	 */
	public static String bytetohex(byte[] bytearray) {
		if(bytearray.length == 0) {
			return new String();
		}
		
		String hex = "";
		
		for(byte b : bytearray) {
			hex += String.format("%02X", b).toLowerCase();
		}
		
		return hex;
	}


}
