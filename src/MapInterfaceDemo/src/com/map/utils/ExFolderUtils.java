package com.map.utils;


public class ExFolderUtils {
	
	public static String decodeFilePath(String root){
		if (root != null && root.toLowerCase().indexOf("http:") == -1
				&& root.indexOf("%20") != -1) {
			root = root.replace("%20", " ");
		}
		return root;
	}
}
