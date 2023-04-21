package com.myezen.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		//�ڵ鷯 (memberLoginAction)�� ����� ���� �� ���ͼ��Ͱ� ȣ��.		
		
		//controller���� rttr�� ���� �𵨿� midx�� �̸��� ����ִ�.
		//�̵��� �����ؼ� �ٽ� ���ǿ� ��� ����.
		Object midx = modelAndView.getModel().get("midx");
		Object memberName = modelAndView.getModel().get("memberName");
		
		if(midx != null) {
			request.getSession().setAttribute("midx", midx);
			request.getSession().setAttribute("memberName", memberName);		
		}
		
		//���ǿ� midx, memberName�� ������ �� view�� ������. view�� �ڵ鷯���� �����ߴ� ���
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		//�α����� �ϱ� �� ����Ǿ��� �κ�. ��, �����ʱ�ȭ.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null) { //������ ������ ��, �ʱ�ȭ
			session.removeAttribute("midx");
			session.removeAttribute("memberName");
			session.invalidate();			
		}
		
		
		return true;
	}
}
