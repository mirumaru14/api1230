package com.myezen.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		//핸들러 (memberLoginAction)가 실행된 이후 이 인터셉터가 호출.		
		
		//controller에서 rttr을 통해 모델에 midx와 이름이 담겨있다.
		//이들을 추출해서 다시 세션에 담는 과정.
		Object midx = modelAndView.getModel().get("midx");
		Object memberName = modelAndView.getModel().get("memberName");
		
		if(midx != null) {
			request.getSession().setAttribute("midx", midx);
			request.getSession().setAttribute("memberName", memberName);		
		}
		
		//세션에 midx, memberName을 세팅한 뒤 view가 생성됨. view는 핸들러에서 설정했던 경로
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		//로그인을 하기 전 실행되야할 부분. 즉, 세션초기화.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null) { //세션이 존재할 시, 초기화
			session.removeAttribute("midx");
			session.removeAttribute("memberName");
			session.invalidate();			
		}
		
		
		return true;
	}
}
