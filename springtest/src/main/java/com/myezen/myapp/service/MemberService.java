package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

public interface MemberService {
	
	//자바 mvc에서는 이곳에 MemberDao 클래스를 만들었지만, 스프링에서는 인터페이스를 만든다 
	
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr, String memberBirth);
	//이곳은 메소드만 만들고, 메소드의 구현은 MemberServiceImpl에 한다.
	
	public ArrayList<MemberVo> memberList();
	
	public int memberIdCheck(String memberId);
	
	public MemberVo memberLogin(String memberId);
	
}
