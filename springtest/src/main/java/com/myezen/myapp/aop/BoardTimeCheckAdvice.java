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
	//�ɸ��� �ð��� üũ�ϴ� �����̽�
	
	private static final Logger logger = LoggerFactory.getLogger(BoardTimeCheckAdvice.class);
	
	@Around("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	//�յڷν���	
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		//ProceedingJoinPoint : Ÿ�ٸ޼ҵ�(�����Ͻ��޼ҵ�) �� ������ ��� Ŭ����.
		//pjp : �� Ŭ������ ����. �ش� �޼ҵ带 �����Ű�ų�, �޼ҵ��� �̸� �ּҵ��� �̾Ƴ�������.
		
		Object result;
		
		logger.info("before AOP");
		logger.info(Arrays.toString(pjp.getArgs()));
		//Ÿ�� �޼ҵ��� �ּ� ���
		
		long startTime = System.currentTimeMillis();
		//���۽ð�
		
		//�߿�!
		//around �����̽� �޼ҵ尡 ����Ǹ�, Ÿ���� �����Ͻ� �޼ҵ�(BoardService)��
		//��������ʴ´�.  around�����̽� �޼ҵ尡 �����Ͻ� �޼ҵ��� ȣ���� ����ë�� ����.
		//pjp.proceed�� �� �����Ͻ� �޼ҵ带 �����ϵ��� �Ѵ�.
		//��, around�����̽� �޼ҵ忡�� pjp.proceed�� ������������ ���� �����̽��� ����Ǵٰ�
		//pjp.proceed�� ������ �����Ͻ� �޼ҵ尡 ����ǰ�, ������ ������ proceed������ �ڵ尡 ����ȴ�.
		result = pjp.proceed();
		//�� �ڵ带 �б������� �����Ͻ� �޼ҵ� ȣ�� ���� �İ� ������.
		//�����Ͻ� �޼ҵ尡 ����� �ڿ��� ObjectŸ���� result�� �����Ͻ� �޼ҵ��� ��������� ����.
		//around�����̽� �޼ҵ尡 ����Ǹ� result�� return�ϰ�,
		//�� ������ Controller�� �������Եȴ�. Controller���� ȣ���� ���̴�.
		
		long endTime = System.currentTimeMillis();
		//�����½ð�
		
		logger.info("After AOP");
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));
		//pjp.getSignature().getName() : Ÿ�� �޼ҵ��� �̸�
		//endTime - startTime = �ɸ��ð�.
		//��, Ÿ�ٸ޼ҵ尡 �Ϸ�Ǵµ��� �ɸ� �ð��� ����.
		logger.info("------time log ��------");
		return result;
	}
	
	
}
