package com.map.service;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.map.common.GlobalConfig;

public class InterfaceService  {
	
	private static final Logger log = LoggerFactory.getLogger(InterfaceService.class);

	
	public InterfaceService(){
		
	}
	
	public String doProcess(String url ,JSONObject json){
		
		if(StringUtils.isBlank(url)){
			url = getGatewayInterfaceUrl();
		}
		
		String resultHtml ="";
		try{
			resultHtml = this.httpPost(url, json.toJSONString());
			// 打印返回结果
			log.debug("响应结果--开始");
			log.debug("响应结果resultHtml:" + resultHtml);
			log.debug("响应结果--结束");
		}catch(IOException ex){
			log.error(ex.getMessage(), ex);
		}
		
		return resultHtml;
	}
	
	protected String getGatewayInterfaceUrl(){
		String url = GlobalConfig.getConfigValue("gateway.interface.url");
		return url;
	}
	
	protected String httpPost(String serviceUrl, String bizData) throws IOException{
		
		String resultText = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
				
			HttpPost httpPost = new HttpPost(serviceUrl);
			
			StringEntity jsonStringEntity = new StringEntity(bizData,"UTF-8");
			jsonStringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "application/json"));
            httpPost.setEntity(jsonStringEntity);
            
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				resultText = EntityUtils.toString(response.getEntity());
			} finally {
				response.close();
			}
			
		} finally {
			httpclient.close();
		}
		
		return resultText;
	}
	
}
