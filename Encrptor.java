package com.krkh.xls.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.poi.util.IOUtils;

public class Encrptor {
	private static final char[] PASSWORD = "dgfgyowhmeolreehaslshetnbalfurtn".toCharArray();
	private static final byte[] SALT = {
        (byte) 0xdd, (byte) 0x34, (byte) 0x11, (byte) 0x12,
        (byte) 0xdd, (byte) 0x34, (byte) 0x11, (byte) 0x12,
    };
	private String userId;
	private String password;
	private String sid;
	private String ipAddress;
	private String portNum;
	private String dbType;
	private String sqLiteDB;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPortNum() {
		return portNum;
	}
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public void writeEncryptDtls(byte[] encrypted){
		File file = new File("./encrypted");
		try {
			FileOutputStream fileOutput = new FileOutputStream(file);
			fileOutput.write(encrypted);
			fileOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private byte[] getBytes(String name, String input){
		String locString = name+":"+input+"\n";
		byte[] bytes = locString.getBytes();
		return bytes;
	}
	public String getSqLiteDB() {
		return sqLiteDB;
	}
	public void setSqLiteDB(String sqLiteDB) {
		this.sqLiteDB = sqLiteDB;
	}
	public void encryptKeys(){
		String input;
		if(dbType.equals("SQLite"))
			input = dbType+"|"+sqLiteDB;
		else
			input = dbType+"|"+ipAddress+"|"+portNum+"|"+sid+"|"+userId+"|"+password;
		System.out.println("input = "+input);
		byte[] encrypted = encrypt(input);
		System.out.println("encrypted = "+encrypted);
		String decrypted = decrypt(encrypted);
		System.out.println("decrypted = "+decrypted);
		writeEncryptDtls(encrypted);
	}
	private byte[] encrypt(String input){
		byte[] encryptedData = null;
		 SecretKeyFactory keyFactory;
		try {
			keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        encryptedData = (byte[])pbeCipher.doFinal(input.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedData;
	}
	 private static String decrypt(byte[] property){
		String decryptedData = "";
        SecretKeyFactory keyFactory;
		try {
			keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        decryptedData =  new String(pbeCipher.doFinal(property), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedData;
    }
	public String decryptFileContent(String fileName){
		String decryptedData = "";
		File file = new File(fileName);
		try {
			InputStream in = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(in);
			decryptedData = decrypt(bytes);
			System.out.println("after file read");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedData;
	}

}
