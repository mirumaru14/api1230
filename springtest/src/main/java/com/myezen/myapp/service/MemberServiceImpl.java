package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.persistance.MemberService_Mapper;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService{
	//add unimplements method : 아직 구현하지 않은 메소드를 추가. ( 자동으로 인터페이스 메소드를 아래에 추가시킴 )
		
	//자동으로 mybatis와 연동하고
	//메소드마다 추가할 필요없이 멤버변수로
	private MemberService_Mapper msm;
	@Autowired
		public MemberServiceImpl(SqlSession sqlSession) {
		this.msm = sqlSession.getMapper(MemberService_Mapper.class);
	}
	
	/*
	//mybatis와 연동
	@Autowired
	SqlSession sqlSession;		//SqlSession클래스는 mybatis에 속해있다. import필요 (mybtis = ibtis. 회사인수문제)
	*/
	@Override				//인터페이스 메소드를 구현
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone,
			String memberEmail, String memberGender, String memberAddr, String memberBirth) {
		
		MemberVo mv = new MemberVo();		//MemberVo는 객체생성
		mv.setMemberid(memberId);			//이부분은 자바와 동일
		mv.setMemberpwd(memberPwd);
		mv.setMembername(memberName);
		mv.setMemberphone(memberPhone);
		mv.setMemberemail(memberEmail);
		mv.setMembergender(memberGender);
		mv.setMemberaddr(memberAddr);
		mv.setMemberbirth(memberBirth);
				
		
		//persistance패키지에 있는 MemberService_Mapper와 연동		
		int value = msm.memberInsert(mv);
		
		return value;
	}

	@Override
	public ArrayList<MemberVo> memberList() {
		
		
		ArrayList<MemberVo> alist = msm.memberList();
		
		return alist;
	}

	@Override
	public int memberIdCheck(String memberId) {
		
		
		int value = msm.memberIdCheck(memberId);
		
		return value;
	}

	@Override
	public MemberVo memberLogin(String memberId) {
		
		MemberVo mv = msm.memberLogin(memberId);
		
		return mv;
	}
	
	
	
	
	

}
