package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
		//AuthInterceptor ���� Ȯ���ϴ� ���ͼ���
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//�ڵ鷯�� ����Ǳ����� �����ϴ� ���ͼ���.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx")==null) {	//�α��� ���� �ʾ��� ��
			//�α��� �� �̵��� �ּҸ� ����.
			saveDest(request);
			
			//�ּҸ� ���� ��, �α���â���� �̵�.
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {
		//���Ѿ���(�α��ξ���)���� ���� ������ �ٸ����(�α���â)�� �̵��� ���, 
		//������ �����(�α��� ��) �ٽ� �̵��Ǳ� ���� ��η� �̵��ؾ��Ѵ�.
		//�� �̵��ϱ� ���� ��θ� �����ϱ� ���� �޼ҵ�
		
		String root = request.getContextPath();
		root.length();
		
		String uri = request.getRequestURI().substring(root.length());	//contextpath�� ����.
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";				
		}else {
			query = "?"+query;		//uri+query�� �̵��� ���. query�� bidx, originbidx��
		}
		
		if(request.getMethod().equals("GET")) {	//url�� �����¹��
			request.getSession().setAttribute("dest", uri+query);
			//���ǿ� dest��� �̸����� �������.
		}
		
	}
	
	
}
