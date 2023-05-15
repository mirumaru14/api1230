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

/* @(Annotation)이란
 * 자바소스코드에 추가하여 사용할 수 있는 메타데이터의 일종.
 * bean 주입 혹은 getter setter 자동생성 등
 * 특별한 의미를 부여하거나 기능부여 등 다양한 역할을 수행.
 * 예시 : xml파일에 직접 써서 bean으로 등록하는것이 아닌, @Component를 사용해서 bean을 등록할 수도 있다.
 * 
 * 스프링의 @ 기능을 보고싶다면 아래의 링크.
 * https://melonicedlatte.com/2021/07/18/182600.html
 * */


@Controller //controller용도의 bean으로 등록. controller용도로 객체생성을 요청.
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	
	/* @RequestMapping : 가상경로와 메소드를 결합시키는 클래스
	 * uri값이 "/" 이면, 아래의 클래스를 실행한다는 뜻.
	 * 클래스의 용도에 따라 value에 board나 member등을 집어넣으면 된다.
	 * @controller안에 있으므로 controller의 역할을 하는 객체.
	 * 모델 : setAtrribute와 비슷함.
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
