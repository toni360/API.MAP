package com.map.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;


public class ExStringUtils {
	
	
	public static String concat(String[] values){
		if (values == null){
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for(String value:values){
			if (buffer.length() > 0){
				buffer.append(",");
			}			
			buffer.append(value);
		}
		return buffer.toString();
	}
	
	public static String concat(String[] values,String split){
		if (values == null){
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for(String value:values){
			if (buffer.length() > 0){
				buffer.append(split);
			}			
			buffer.append(value);
		}
		return buffer.toString();
	}
	
	
	public static boolean isBlank(String argStr){
		return StringUtils.isBlank(argStr);
	}
	
	public static boolean isNotBlank(String argStr){
		return StringUtils.isNotBlank(argStr);
	}
	
	public static boolean isEmpty(String argStr){
		return StringUtils.isEmpty(argStr);
	}
	
	public static boolean isNotEmpty(String argStr){
		return StringUtils.isNotEmpty(argStr);
	}
	
	public static String getDocDesc(String docValue){
		if (docValue != null){
			int beginIndex = docValue.indexOf("[");
			return docValue.substring(0, beginIndex);
		}
		return null;
	}
	

	
	
	/**
	 * 随机码
	 * @param codeLen
	 * @return
	 */
	public static String getVerifyCode(int codeLen) {  
        int count = 0;  
        char str[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  
        StringBuffer verifyCode = new StringBuffer("");  
        Random r = new Random();  
        while (count < codeLen) {  
            int i = Math.abs(r.nextInt(10));  
            if (i >= 0 && i < str.length) {  
            	verifyCode.append(str[i]);  
                count++;  
            }  
        }  
        return verifyCode.toString();  
    }
	
	public static String genNonce(){		
		Random r = new Random();		
		return String.valueOf(r.nextLong());
	}
}
