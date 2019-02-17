package hr.fer.zemris.java.hw07.crypto;

/**
 * Util class for programe {@link Crypto}.
 * Contains methods for hex to bxte and bxte to hex conversions.
 * @author Alex
 *
 */
public class Util {
	
	/**
	 * Letters used in hexadecimal numbers.
	 */
	private static String letters = "abcdef";
	
	/**
	 * Conversion of hexadecimal data to bytes.
	 * @param keyText text to be converted.
	 * @return byte representation of keyText.
	 * @throws IllegalArgumentException if keyText is invalid.
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() == 0) {
			return new byte[0];
		}
		
		if(keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("Cannot convert keytext to bytes, len is odd: " + keyText);
		}
		
		byte[] bytes = new byte[keyText.length() / 2];
		int bcounter = 0;
		
		for(int i = 0; i < keyText.length(); i += 2) {
			String s = keyText.substring(i, i + 2).toLowerCase();
			isValid(s);
			bytes[bcounter++] = (byte) ((Character.digit(s.charAt(0), 16) << 4)
											+ Character.digit(s.charAt(1), 16));
		}
		
		return bytes;
	}
	
	/**
	 * Conversion of bytes to text.
	 * @param bytearray bytes to be converted to text.
	 * @return text representation of bytearray.
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
	
	/**
	 * Checks if text is valid as hexadecimal number.
	 * @param s text to be checked.
	 */
	private static void isValid(String s) {
		if(!Character.isDigit(s.charAt(0)) && !letters.contains(s.substring(0, 1)) ||
				!Character.isDigit(s.charAt(1)) && !letters.contains(s.substring(1))) {
			throw new IllegalArgumentException("Input is not letter or digit: " + s);
		}
	}

}
