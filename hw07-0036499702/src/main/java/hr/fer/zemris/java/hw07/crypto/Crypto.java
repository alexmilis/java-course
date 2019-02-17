package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program used to encrypt and decrypt data.
 * It also contains a method for checking if digest is valid with algorithm sha-256.
 * @author Alex
 *
 */
public class Crypto {

	/**
	 * Main method that executes command given by user in command line.
	 * @param args arguments from command line. Expected arguments are command and path(s).
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws ShortBufferException
	 */
	public static void main(String[] args) throws BadPaddingException, InvalidKeyException, 
			NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			IllegalBlockSizeException,  ShortBufferException {
		
		if(args.length < 2) {
			throw new IllegalArgumentException("Invalid arguments from command line");
		}
		
		switch(args[0].toLowerCase()) {
		case "checksha":
			checksha(args[1]);
			break;
		case "encrypt":
			crypt(true, args[1], args[2]);
			break;
		case "decrypt":
			crypt(false, args[1], args[2]);
			break;
		default:
			throw new IllegalArgumentException("Invalid keyword: " + args[0]);
		}
		
	}

	/**
	 * Checks if given digest equals calculated digest. Algorithm used is sha-256.
	 * @param filename file for which digest is being checked.
	 * @throws NoSuchAlgorithmException
	 */
	public static void checksha(String filename) throws NoSuchAlgorithmException {
		
		Path p = Paths.get("./" + filename);
		
		System.out.println("Please provide expected sha-256 digest for hw06part2.pdf:\n> ");
		Scanner sc = new Scanner(System.in);
		String expected = sc.next();
		sc.close();

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] buff = new byte[4096];
		
		try {
			InputStream in = new BufferedInputStream(Files.newInputStream(p));
			int r = in.read(buff);
			while(r != -1) {
				md.update(buff, 0, r);
				r = in.read(buff);
			}
			
			in.close();
		} catch (IOException ex) {
			System.out.println("An error occurred while reading the document: " + ex.getMessage());
			return;
		}
			

		String digest = Util.bytetohex(md.digest()).toLowerCase();
		
		if(digest.equals(expected)) {
			System.out.println("Digesting completed. Digest of " + filename + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest.\nDigest was: " + digest);
		}
	}
	
	/**
	 * Encrypts/decrypts a file and saves the result in a file with given path.
	 * @param encrypt 	true -> encrypt
	 * 					false -> decrypt
	 * @param original	file to be encrypted/decrypted
	 * @param crypted  	file in which result should be saved.
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ShortBufferException
	 */
	public static void crypt(boolean encrypt, String original, String crypted) 
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, 
			InvalidAlgorithmParameterException, IllegalBlockSizeException, 
			BadPaddingException, ShortBufferException {
		
		Cipher cipher = getCipher(encrypt);
		
		Path p1 = Paths.get("./" + original);
		Path p2 = Paths.get("./" + crypted);
		byte[] buff = new byte[4096];
		byte[] buff2 = new byte[5120];

		try {
			InputStream in = new BufferedInputStream(Files.newInputStream(p1));
			OutputStream out = new BufferedOutputStream(Files.newOutputStream(p2, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
			int r = in.read(buff);
			while(r != -1) {
				int r2 = cipher.update(buff, 0, r, buff2);
				out.write(buff2, 0, r2);
				out.flush();
				r = in.read(buff);
			}
			
			int r2 = cipher.doFinal(buff2, 0);
			out.write(buff2, 0, r2);
			
			in.close();
			out.close();
		} catch (IOException ex) {
			System.out.println("An error occurred while reading the document: " + ex.getMessage());
			return;
		}
		
		if(encrypt) {
			System.out.println("Encryption completed. Generated file " + crypted + " based on file " + original + ".");
		} else {
			System.out.println("Decryption completed. Generated file " + crypted + " based on file " + original + ".");
		}
		
		
	}
	
	/**
	 * Gets cipher for encryption/decryption.
	 * @param encrypt	true -> encrypt
	 * 					false -> decrypt
	 * @return cipher
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	private static Cipher getCipher(boolean encrypt) throws NoSuchAlgorithmException, 
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		
		Scanner sc = new Scanner(System.in);
		
		String keyText = "";
		while(keyText.length() != 32) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			keyText = sc.next();
			System.out.println(keyText);
		}
			
		String ivText = "";
		while(ivText.length() != 32) {
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			ivText = sc.next();
			System.out.println(ivText);
		}
		
		sc.close();
				
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
	
		return cipher;
	}
	
	

}
