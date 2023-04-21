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
public class MemberController {		//경로값이 /member이면 이 객체 생성
	
	
	@Autowired 	//객체의 타입이 일치하는지 확인. 일치하면 객체를 자동으로 주입. bean으로 등록한 객체주입
	MemberService ms;
	//
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping(value="/memberJoin.do")	
	public String memberJoin() {	//경로값이 /member/memberJoin.do 이면 이 메소드 실행
		
		
		
		return "member/memberJoin";	//  WEB-INF/views/member/memberJoin.jsp   경로중 views/ ( 여기 ).jsp
		// /member/memberJoin.do경로에 들어가게되면, WEB-INF/views/member/memberJoin.jsp을 보여준다.				
	}
	
	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(		//매개변수 부분에 @RequestParam클래스 사용.
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberGender") String memberGender,
			@RequestParam("memberAddr") String memberAddr,
			@RequestParam("memberBirth") String memberBirth
			) {
		
		//회원의 패스워드를 암호화. 암호화 한 패스워드는 memberPwd2
		String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);
				
		// 자바상에서는 MemberDao를 객체생성시키지만 Spring은 다르게 진행.
		// 15~16행의 @Autowired MemberService
		int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);
		
		
		return "redirect:/";		//root로 보내면 자동으로 index.jsp로 보내지게됨
	}
	
	
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
		
		return "member/memberList";
	}
	
	
	@ResponseBody	//객체를 리턴해야하므로 ResponseBody를 사용
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {
		
		String str = null;
		int value = 0;
		
		value = ms.memberIdCheck(memberId);
		
		//json 파일 형태로 보내야함 (객체)
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
			HttpSession session,		//매개변수에 세션
			RedirectAttributes rttr		//모델클래스. 1회성기능
			) {
		
		MemberVo mv = ms.memberLogin(memberId);		
		
		//경로. 사용할 변수만 선언
		String path="";
		
		//회원의 패스워드는 암호화되어있음.
		//로그인창에 입력한 비밀번호 memberPwd와 db상에 등록된 암호화된 패스워드를 대조
		
		if(mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
			
			//컨트롤러에서 세션기능을 쓰는것은 좋지않다. 세션은 인터셉터에게 맡김.
			//인터셉터가 꺼내 쓸 수 있도록 rttr 즉 모델에 midx와 memberName을 담음.
			//인터셉터는 여기에 담긴 모델들을 꺼내서 세션에 다시 옮겨담을것임.
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
						
			if (session.getAttribute("dest")==null) {
				path="redirect:/";
			}else{
				String dest = (String)session.getAttribute("dest");
				//인터셉터를통해 세션에 dest라는 이름으로 경로를 저장했음. 이 경로를 가져옴.
				path="redirect:"+dest;
				//가져온 경로는 contextPath가 없어야됨. 인터셉터에서 미리 없애뒀음.
			}			
			//로그인 성공시 전에 있던 화면으로.
		}else {
			rttr.addFlashAttribute("msg", "아이디 혹은 비밀번호를 확인해주세요"); //1회성 메소드
			path="redirect:/member/memberLogin.do";
			//로그인 실패시 다시 로그인창으로
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
