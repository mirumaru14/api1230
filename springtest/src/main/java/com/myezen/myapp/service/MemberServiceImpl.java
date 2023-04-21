package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.persistance.MemberService_Mapper;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService{
	//add unimplements method : ���� �������� ���� �޼ҵ带 �߰�. ( �ڵ����� �������̽� �޼ҵ带 �Ʒ��� �߰���Ŵ )
		
	//�ڵ����� mybatis�� �����ϰ�
	//�޼ҵ帶�� �߰��� �ʿ���� ���������
	private MemberService_Mapper msm;
	@Autowired
		public MemberServiceImpl(SqlSession sqlSession) {
		this.msm = sqlSession.getMapper(MemberService_Mapper.class);
	}
	
	/*
	//mybatis�� ����
	@Autowired
	SqlSession sqlSession;		//SqlSessionŬ������ mybatis�� �����ִ�. import�ʿ� (mybtis = ibtis. ȸ���μ�����)
	*/
	@Override				//�������̽� �޼ҵ带 ����
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone,
			String memberEmail, String memberGender, String memberAddr, String memberBirth) {
		
		MemberVo mv = new MemberVo();		//MemberVo�� ��ü����
		mv.setMemberid(memberId);			//�̺κ��� �ڹٿ� ����
		mv.setMemberpwd(memberPwd);
		mv.setMembername(memberName);
		mv.setMemberphone(memberPhone);
		mv.setMemberemail(memberEmail);
		mv.setMembergender(memberGender);
		mv.setMemberaddr(memberAddr);
		mv.setMemberbirth(memberBirth);
				
		
		//persistance��Ű���� �ִ� MemberService_Mapper�� ����		
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