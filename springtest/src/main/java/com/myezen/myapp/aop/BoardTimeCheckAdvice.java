package com.myezen.myapp.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BoardTimeCheckAdvice {
	//걸리는 시간을 체크하는 어드바이스
	
	private static final Logger logger = LoggerFactory.getLogger(BoardTimeCheckAdvice.class);
	
	@Around("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	//앞뒤로실행	
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		//ProceedingJoinPoint : 타겟메소드(비지니스메소드) 의 정보를 담는 클래스.
		//pjp : 그 클래스의 변수. 해당 메소드를 진행시키거나, 메소드의 이름 주소등을 뽑아낼수있음.
		
		Object result;
		
		logger.info("before AOP");
		logger.info(Arrays.toString(pjp.getArgs()));
		//타겟 메소드의 주소 출력
		
		long startTime = System.currentTimeMillis();
		//시작시간
		
		//중요!
		//around 어드바이스 메소드가 실행되면, 타겟인 비지니스 메소드(BoardService)는
		//실행되지않는다.  around어드바이스 메소드가 비지니스 메소드의 호출을 가로챘기 때문.
		//pjp.proceed는 그 비지니스 메소드를 진행하도록 한다.
		//즉, around어드바이스 메소드에서 pjp.proceed가 나오기전까지 먼저 어드바이스가 실행되다가
		//pjp.proceed가 나오면 비지니스 메소드가 진행되고, 진행이 끝나면 proceed이후의 코드가 진행된다.
		result = pjp.proceed();
		//위 코드를 분기점으로 비지니스 메소드 호출 전과 후가 나뉜다.
		//비지니스 메소드가 진행된 뒤에는 Object타입의 result에 비지니스 메소드의 결과값들이 담긴다.
		//around어드바이스 메소드가 종료되면 result를 return하고,
		//이 값들을 Controller로 가져가게된다. Controller에서 호출한 값이다.
		
		long endTime = System.currentTimeMillis();
		//끝나는시간
		
		logger.info("After AOP");
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));
		//pjp.getSignature().getName() : 타겟 메소드의 이름
		//endTime - startTime = 걸린시간.
		//즉, 타겟메소드가 완료되는데에 걸린 시간을 뜻함.
		logger.info("------time log 끝------");
		return result;
	}
	
	
}
