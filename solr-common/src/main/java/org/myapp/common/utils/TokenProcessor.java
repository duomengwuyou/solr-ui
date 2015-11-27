package org.myapp.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;


//令牌，用于参数一个随机唯一的令牌值  
public class TokenProcessor{  
	private TokenProcessor(){}  
	private static final TokenProcessor token = new TokenProcessor();  
	public static TokenProcessor getInstance(){  
		return token;  
	}  
	public String generateToken(){  
		String token = System.currentTimeMillis() +  UUID.randomUUID().toString();    //随机 不重复的token  
		try {  
			MessageDigest md = MessageDigest.getInstance("md5");        //注意下面的处理方式  
			byte[] md5 = md.digest(token.getBytes());  
			return Base64.encodeBase64String(md5);    
		} catch (NoSuchAlgorithmException e) {  
			throw new RuntimeException(e);  
		}  
	}  
}
