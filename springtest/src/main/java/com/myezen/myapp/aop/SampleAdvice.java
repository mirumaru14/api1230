package com.myezen.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //이 클래스를 Bean으로 등록
@Aspect	//프록시 생성기가 이 어노테이션(@Aspect)를 찾아서 이 클래스를 어드바이저로 만들어줌.
//Aspect = Advice + PoinCut.
//아래의 클래스는 부가기능이며, 그 부가기능을 정의한 코드가 Advice.
//이 어드바이스를 어디에 적용할지 결정하는것이 PointCut. 즉 어떤기능을 어디에 적용할지 결정되었다는 뜻임
public class SampleAdvice {
	
	//Logger타입은 org.slf4j.Logger를 임포트
	private static final Logger logger=LoggerFactory.getLogger(SampleAdvice.class);
		
	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))")	//aspectj 문법을 사용
	//@Before�� PointCut. 이 기능이 언제 적용될지 알려줌. 적용되는 위치는 aspectj문법으로 작성되었다.
	//BoardService로 시작하는 모든 객체
	public void startLog() {
		//BoardService의 객체가 실행될때마다 그 객체 이전에 이 메소드가 먼저 실행됨?
		
		logger.info("-----------------------");
		logger.info("aop 로그테스트중입니다.");
		logger.info("-----------------------");
		System.out.println("sysout 입니다.");
	}
}
