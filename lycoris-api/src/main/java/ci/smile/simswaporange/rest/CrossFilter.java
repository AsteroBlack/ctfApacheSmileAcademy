///*
// * Created on 2019-03-05 ( Time 12:31:18 )
// * Generator tool : Telosys Tools Generator ( version 3.0.0 )
// * Copyright 2018 Savoir Faire Linux. All Rights Reserved.
// */
//package ci.smile.simswaporange.rest;
//
//import java.io.IOException;
///*import java.util.UUID;*/
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///*import org.json.JSONObject;*/
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
///**
// * Servlet Filter implementation class CrossFilter
// */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CrossFilter implements Filter {
//
//	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private Environment environment;
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		// place your code here
//		slf4jLogger.info("begin doFilter");
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
//		httpServletResponse.setHeader("Access-Control-Allow-Headers",
//				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Show-Success-Message, Show-Loader, Show-Error-Message, sessionUser,lang,user,serviceLibelle,ndCode,customerCode");
//		chain.doFilter(request, response);
//		slf4jLogger.info("end doFilter");
//	}
//
//	@Override
//	public void init(FilterConfig fConfig) throws ServletException {
//		// TODO Auto-generated method stub
//	}
//	
//}