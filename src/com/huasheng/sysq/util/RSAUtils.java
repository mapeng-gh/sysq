package com.huasheng.sysq.util;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtils {

	/**
	 * 私钥解密
	 * @param encryptStr 待解密串
	 * @param privateKeyStr 私钥
	 * @return
	 */
	public static String decrypt(String encryptStr,String privateKeyStr){
		
		try{
			//准备数据：解密串转化字节、私钥串转化PrivateKey
			byte[] eccryptBytes = hexStringToBytes(encryptStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(hexStringToBytes(privateKeyStr));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
	        
	        //构造Cipher
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        
	        //解密
	        byte[] decryptBytes = cipher.doFinal(eccryptBytes);
	        String decryptStr = new String(decryptBytes,"UTF-8");
	        return decryptStr;
		}catch(Exception e){
			throw new RuntimeException("解密失败",e);
		}
		
	}
	
	/**
	 * 公钥加密
	 * @param data 待加密数据
	 * @param publicKeyString 公钥
	 * @return
	 */
	public static String encrypt(String data,String publicKeyStr){
		
		try{
			//数据准备：data转化字节数组，公钥字符串转化为PublicKey
			byte[] dataBytes = data.getBytes("UTF-8");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(hexStringToBytes(publicKeyStr));  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        PublicKey publicKey = keyFactory.generatePublic(keySpec); 
	        
	        //构造Cipher
	        Cipher  cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        
	        //分段加密
	        int MAX_ENCRYPT_BLOCK = 117;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;    
	        byte[] cache; 
	        int dataLength = dataBytes.length;
	        try{
	        	 while(offSet < dataLength){
	 	        	if (dataLength - offSet > MAX_ENCRYPT_BLOCK) {    
	 	                cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);    
	 	            } else {    
	 	                cache = cipher.doFinal(dataBytes, offSet, dataLength - offSet);    
	 	            }    
	 	            out.write(cache, 0, cache.length);    
	 	            offSet = offSet + MAX_ENCRYPT_BLOCK;    
	 	        }
	        	
	        	//输出
	 	        byte[] encryptedBytes = out.toByteArray();    
	 	        out.close();    
	 	        String encryptedStr = bytesToHexString(encryptedBytes);
	 	        return encryptedStr;   
	 	        
	        }catch(Exception e){
	        	throw e;
	        }finally{
	        	out.close();
	        }
	        
		}catch(Exception e){
			throw new RuntimeException("解密失败", e);
		}
	}
	
	/**
	 * 生成公私密钥对
	 * @return
	 */
	public static Map<String,String> generateKeys(){
		
		try{
			Map<String,String> keys = new HashMap<String,String>();
			
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			byte[] publicKeys = publicKey.getEncoded();
			byte[] privateKeys = privateKey.getEncoded();
			
			keys.put("publicKey", bytesToHexString(publicKeys));
			keys.put("privateKey", bytesToHexString(privateKeys));
			
			return keys;
			
		}catch(Exception e){
			throw new RuntimeException("生成公私密钥对失败", e);
		}
	}
	
	/**
	 * 十六进制字符串转化字节数组：每两个十六进制串分割，转化为十进制，再转化为字节
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString){
		if (hexString == null || hexString.length() <= 0) {
	        return null;   
	    }
		
		if(hexString.length()%2 != 0){
			return null;
		}
		
		byte[] bytes = new byte[hexString.length()/2];
		int index = 0;
		while(hexString.length() > 0){
			String hexStr = hexString.substring(0, 2);
			int d = Integer.parseInt(hexStr, 16);
			bytes[index] = (byte)d;
			index++;
			hexString = hexString.substring(2);
		}
		return bytes;
	}
	
	/**
	 * 字节数组转化十六进制字符串：先字节转化为int，然后利用Integer方法转化十六进制串，最后补上前缀0
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	} 
}
