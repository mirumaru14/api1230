package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.MemberVo;



public interface MemberService_Mapper {
	
	//마이바티스의 사용할 메소드를 정의한다
	
	//public MemberVo memberLogin(String id, String pwd);
	
	
	public int memberInsert(MemberVo mv);
	
	public ArrayList<MemberVo> memberList();
	
	public int memberIdCheck(String memberId);	
	
	public MemberVo memberLogin(String memberId);
	
}
