package com.myezen.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //�� Ŭ������ Bean���� ���
@Aspect	//���Ͻ� �����Ⱑ �� ������̼�(@Aspect)�� ã�Ƽ� �� Ŭ������ ���������� �������.
//Aspect = Advice + PoinCut.
//�Ʒ��� Ŭ������ �ΰ�����̸�, �� �ΰ������ ������ �ڵ尡 Advice.
//�� �����̽��� ��� �������� �����ϴ°��� PointCut. �� ������ ��� �������� �����Ǿ��ٴ� ����.
public class SampleAdvice {
	
	//LoggerŸ���� org.slf4j.Logger �� ����Ʈ	
	private static final Logger logger=LoggerFactory.getLogger(SampleAdvice.class);
		
	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))")	//aspectj ������ ��� 
	//@Before�� PointCut. �� ����� ���� ������� �˷���. ����Ǵ� ��ġ�� aspectj�������� �ۼ��Ǿ���.
	//BoardService�� �����ϴ� ��� ��ü
	public void startLog() {
		//BoardService�� ��ü�� ����ɶ����� �� ��ü������ �� �޼ҵ尡 ���������?
		
		logger.info("-----------------------");
		logger.info("aop �α��׽�Ʈ���Դϴ�.");
		logger.info("-----------------------");
		System.out.println("sysout �Դϴ�.");
	}
}
