package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

public interface MemberService {
	
	//�ڹ� mvc������ �̰��� MemberDao Ŭ������ ���������, ������������ �������̽��� ����� 
	
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr, String memberBirth);
	//�̰��� �޼ҵ常 �����, �޼ҵ��� ������ MemberServiceImpl�� �Ѵ�.
	
	public ArrayList<MemberVo> memberList();
	
	public int memberIdCheck(String memberId);
	
	public MemberVo memberLogin(String memberId);
	
}
