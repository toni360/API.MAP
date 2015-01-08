package com.map.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.map.utils.ExFolderUtils;

public class GlobalConfig {

	private static Log log = LogFactory.getLog(GlobalConfig.class);
	private static Properties configProperties = null;


	static {
		reloadConfig();
	}

	public synchronized static void reloadConfig() {
		if (configProperties == null) {
			configProperties = new Properties();
		} else {
			configProperties.clear();
		}
		loadProperties(configProperties, "config.properties");
	}

	private static void loadProperties(Properties properties, String fileName) {
		if (properties == null || fileName == null) {
			return;
		}
		try {
			String root = GlobalConfig.class.getResource("/").getPath();
			
			root = ExFolderUtils.decodeFilePath(root);//added on 20121205
			String file = root + fileName;
			log.debug("loadProperties()-- fileName=[" + file + "]");
			InputStream is = new FileInputStream(file);
			properties.load(is);
			is.close();
		} catch (Exception e) {
			log.error("loadProperties()[" + fileName + "]:" + e);
		}
	}

	public static String getConfigValue(String key) {
		if (configProperties == null) {
			return null;
		}
		return configProperties.getProperty(key);
	}
}
