package com.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.map.common.GlobalConfig;
import com.map.service.InterfaceService;

/**
 * Servlet implementation class InterfaceDemoServlet
 */
@WebServlet("/map/demo/*")
public class InterfaceDemoServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6420642312192446329L;
	private static final Logger log = LoggerFactory.getLogger(InterfaceDemoServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterfaceDemoServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 执行接入处理服务
		this.execute(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 执行接入处理服务
		this.execute(request, response);			

	}

	protected void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String jsonStr = IOUtils.toString(request.getInputStream(),"UTF-8");
		
		JSONObject json = JSONObject.parseObject(jsonStr);
		
		InterfaceService interfaceService = new InterfaceService();
		String result = interfaceService.doProcess(getInterfaceUrl()+getMethod(request.getServletPath()),json);
		System.out.println("接口返回:"+result);
		log.info("接口返回:"+result);
		
		response.setContentType("text/html"); // 设置文档类型为HTML
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}
	protected String getInterfaceUrl(){
		String url = GlobalConfig.getConfigValue("gateway.interface.url");
		return url;
	}
	
	protected String getMethod(String url){
		String method = url.replaceFirst("/map/demo", "");
		return method;
	}
	
}
