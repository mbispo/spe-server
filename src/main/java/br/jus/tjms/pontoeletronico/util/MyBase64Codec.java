package br.jus.tjms.pontoeletronico.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class MyBase64Codec {

	public static void main(String[] args) {
//		String string = "SmF2YWNvZGVnZWVrcw==";
//		// Get bytes from string
//		byte[] byteArray = Base64.decodeBase64(string.getBytes());
//		
//		String aaaa = Base64.encodeBase64String(byteArray);
//		
//		// Print the decoded array
//		System.out.println(Arrays.toString(byteArray));
//		// Print the decoded string
//		String decodedString = new String(byteArray);
//		System.out.println(string + " = " + decodedString);
//		System.out.println("\n\n\n\n");
//		System.out.println(aaaa);
//		System.out.println(byteArray);
		
		
		try {
			
			byte[] array = Files.readAllBytes(new File("D:/temp/a.png").toPath());
			
			
			String serializado = java.net.URLEncoder.encode(MyBase64Codec.serialize(array),"UTF-8"); 
			
			System.out.println(serializado);
			
			byte[] array2 = MyBase64Codec.deserialize(java.net.URLDecoder.decode(serializado,"UTF-8"));
			
			System.out.println(array2);
			System.out.println(Arrays.toString(array2));
			
			File f2 = new File("D:/temp/b.png");
			
			if (f2.exists()) {
				Files.delete(f2.toPath());
			}
			
			Files.write(f2.toPath(), array2, StandardOpenOption.CREATE_NEW);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	private static String serialize(byte[] input) {
		
		return Base64.encodeBase64String(input);
		
		
	}
	
	
	public static byte[] deserialize(String input) {
	
		return Base64.decodeBase64(input);
		
	}
	
}
