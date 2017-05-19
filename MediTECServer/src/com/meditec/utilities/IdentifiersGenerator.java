package com.meditec.utilities;

import java.security.SecureRandom;

public class IdentifiersGenerator {
	
	private static SecureRandom rd = new SecureRandom();
	
	/**
	 * @param lenght el largo del id.
	 * @return un id aleatorio.
	 */
	public static String generate_new_code(int lenght){
		
		final String OPTIONS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return sb.toString();	
	}
	
	/**
	 * 
	 * @param lenght el largo del key.
	 * @return un key numérico aleatorio.
	 */
	public static int generate_new_key(int lenght){
		
		final String OPTIONS = "0123456789";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return Integer.parseInt(sb.toString());
	}
	
	/**
	 * @param lenght el largo del nombre.
	 * @return un nombre aleatorio.
	 */
	public static String generate_name(int lenght){
		final String OPTIONS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		StringBuilder sb = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++){
			sb.append(OPTIONS.charAt(rd.nextInt(OPTIONS.length())));
		}
		return sb.toString();
	}
	
}
