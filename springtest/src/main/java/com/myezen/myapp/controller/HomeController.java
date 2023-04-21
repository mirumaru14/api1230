package com.myezen.myapp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* @(Annotation)�̶�
 * �ڹټҽ��ڵ忡 �߰��Ͽ� ����� �� �ִ� ��Ÿ�������� ����.
 * bean ���� Ȥ�� getter setter �ڵ����� �� 
 * Ư���� �ǹ̸� �ο��ϰų� ��ɺο� �� �پ��� ������ ����.
 * ���� : xml���Ͽ� ���� �Ἥ bean���� ����ϴ°��� �ƴ�, @Component�� ����ؼ� bean�� ����� ���� �ִ�.
 * 
 * �������� @ ����� ����ʹٸ� �Ʒ��� ��ũ.
 * https://melonicedlatte.com/2021/07/18/182600.html
 * */


@Controller //controller�뵵�� bean���� ���. controller�뵵�� ��ü������ ��û.
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	
	/* @RequestMapping : �����ο� �޼ҵ带 ���ս�Ű�� Ŭ����
	 * uri���� "/" �̸�, �Ʒ��� Ŭ������ �����Ѵٴ� ��.
	 * Ŭ������ �뵵�� ���� value�� board�� member���� ��������� �ȴ�.
	 * @controller�ȿ� �����Ƿ� controller�� ������ �ϴ� ��ü.
	 * �� : setAttribute �� �����.	 
	 * */
	
	
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
