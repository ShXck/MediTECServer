package com.meditec.utilities;

import java.security.SecureRandom;

public class IdentifiersGenerator {
	
	private static SecureRandom rd = new SecureRandom();
	
	public static String generate_new_code(int lenght){
		
		final String OPTIONS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return sb.toString();	
	}
	
	public static int generate_new_key(int lenght){
		
		final String OPTIONS = "0123456789";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return Integer.parseInt(sb.toString());
	}
	
	public static String generate__name(int lenght){
		final String OPTIONS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return sb.toString();
	}
	
}
