package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
		//AuthInterceptor 권한 확인하는 인터셉터
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//핸들러가 실행되기전에 동작하는 인터셉터.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx")==null) {	//로그인 하지 않았을 시
			//로그인 후 이동할 주소를 담음.
			saveDest(request);
			
			//주소를 담은 뒤, 로그인창으로 이동.
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {
		//권한없음(로그인안함)으로 인해 강제로 다른경로(로그인창)로 이동된 경우, 
		//권한이 생긴뒤(로그인 후) 다시 이동되기 전의 경로로 이동해야한다.
		//이 이동하기 전의 경로를 저장하기 위한 메소드
		
		String root = request.getContextPath();
		root.length();
		
		String uri = request.getRequestURI().substring(root.length());	//contextpath를 제외.
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";				
		}else {
			query = "?"+query;		//uri+query가 이동할 경로. query는 bidx, originbidx등
		}
		
		if(request.getMethod().equals("GET")) {	//url을 보내는방식
			request.getSession().setAttribute("dest", uri+query);
			//세션에 dest라는 이름으로 경로저장.
		}
		
	}
	
	
}
