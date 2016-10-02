package com.example;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.glassfish.grizzly.utils.Charsets;

/**
 * https://github.com/auth0/java-jwt
 * @author fil
 *
 */
public class Utils {
	public static String toSHA1(byte[] convertme) {
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return Base64.getEncoder().encodeToString(md.digest(convertme));
	}
	
	public static void main(String[] args) {
		System.out.println(toSHA1("fedora".getBytes(Charsets.UTF8_CHARSET)));
	}
	
}
