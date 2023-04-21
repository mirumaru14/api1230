package com.myezen.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {		//��ΰ��� /member�̸� �� ��ü ����
	
	
	@Autowired 	//��ü�� Ÿ���� ��ġ�ϴ��� Ȯ��. ��ġ�ϸ� ��ü�� �ڵ����� ����. bean���� ����� ��ü����
	MemberService ms;
	//
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping(value="/memberJoin.do")	
	public String memberJoin() {	//��ΰ��� /member/memberJoin.do �̸� �� �޼ҵ� ����
		
		
		
		return "member/memberJoin";	//  WEB-INF/views/member/memberJoin.jsp   ����� views/ ( ���� ).jsp
		// /member/memberJoin.do��ο� ���ԵǸ�, WEB-INF/views/member/memberJoin.jsp�� �����ش�.				
	}
	
	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(		//�Ű����� �κп� @RequestParamŬ���� ���.
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberGender") String memberGender,
			@RequestParam("memberAddr") String memberAddr,
			@RequestParam("memberBirth") String memberBirth
			) {
		
		//ȸ���� �н����带 ��ȣȭ. ��ȣȭ �� �н������ memberPwd2
		String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);
				
		// �ڹٻ󿡼��� MemberDao�� ��ü������Ű���� Spring�� �ٸ��� ����.
		// 15~16���� @Autowired MemberService
		int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);
		
		
		return "redirect:/";		//root�� ������ �ڵ����� index.jsp�� �������Ե�
	}
	
	
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
		
		return "member/memberList";
	}
	
	
	@ResponseBody	//��ü�� �����ؾ��ϹǷ� ResponseBody�� ���
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {
		
		String str = null;
		int value = 0;
		
		value = ms.memberIdCheck(memberId);
		
		//json ���� ���·� �������� (��ü)
		str = "{\"value\": \""+value+"\"}";
		
		return str;
	}
	
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		
		return "member/memberLogin";
	}
	
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			HttpSession session,		//�Ű������� ����
			RedirectAttributes rttr		//��Ŭ����. 1ȸ�����
			) {
		
		MemberVo mv = ms.memberLogin(memberId);		
		
		//���. ����� ������ ����
		String path="";
		
		//ȸ���� �н������ ��ȣȭ�Ǿ�����.
		//�α���â�� �Է��� ��й�ȣ memberPwd�� db�� ��ϵ� ��ȣȭ�� �н����带 ����
		
		if(mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
			
			//��Ʈ�ѷ����� ���Ǳ���� ���°��� �����ʴ�. ������ ���ͼ��Ϳ��� �ñ�.
			//���ͼ��Ͱ� ���� �� �� �ֵ��� rttr �� �𵨿� midx�� memberName�� ����.
			//���ͼ��ʹ� ���⿡ ��� �𵨵��� ������ ���ǿ� �ٽ� �Űܴ�������.
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
						
			if (session.getAttribute("dest")==null) {
				path="redirect:/";
			}else{
				String dest = (String)session.getAttribute("dest");
				//���ͼ��͸����� ���ǿ� dest��� �̸����� ��θ� ��������. �� ��θ� ������.
				path="redirect:"+dest;
				//������ ��δ� contextPath�� ����ߵ�. ���ͼ��Ϳ��� �̸� ���ֵ���.
			}			
			//�α��� ������ ���� �ִ� ȭ������.
		}else {
			rttr.addFlashAttribute("msg", "���̵� Ȥ�� ��й�ȣ�� Ȯ�����ּ���"); //1ȸ�� �޼ҵ�
			path="redirect:/member/memberLogin.do";
			//�α��� ���н� �ٽ� �α���â����
		}
		
		return path;
	}
	
	@RequestMapping(value="/memberLogOut.do")
	public String memberLogOut(HttpSession session) {
		
		session.removeAttribute("midx");
		session.removeAttribute("memberName");
		session.invalidate();
		
		return "redirect:/";
	}
	
	
}
