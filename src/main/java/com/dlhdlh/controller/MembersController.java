package com.dlhdlh.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.service.MembersService;

@Controller
public class MembersController {
	
	@Autowired
	MembersService membersService;
	
	
	// 로그인 페이지
	@RequestMapping("/members/login")
	public String LoginPage() {
		return "Members/login";
	}
	
	// 회원가입 페이지
	@RequestMapping("/members/signin")
	public String SignInPage() {
		return "Members/signin";
	}
	
	// 로그인 submit 프로세스
	@ResponseBody 
	@RequestMapping(value = "members/loginAjax", method = RequestMethod.POST)
	public Object LoginProcessAjax(MembersDto membersDto, HttpServletRequest request) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		int resultCnt = membersService.LoginCheck(membersDto);

		map.put("resultCnt", resultCnt);
		
		if(resultCnt > 0){
			MembersDto getMemberDto = membersService.SelectMemberDto(membersDto.getUserId());
			HttpSession session = request.getSession();
			session.setAttribute("userId", getMemberDto.getUserId());
			session.setAttribute("userName", getMemberDto.getUserName());
			session.setMaxInactiveInterval(1800); //초 단위 10분 600초	
			return map;
		}
		else {
			return map;
		}
	}
	
	// 로그아웃
	@RequestMapping(value="members/logout") 
	public String logout(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	// 회원가입 submit 프로세스
	@ResponseBody 
	@RequestMapping(value = "member/signInAjax", method = RequestMethod.POST)
	public Object signInProcess(MembersDto membersDto, HttpServletRequest request) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		int resultCnt = membersService.UserIdCheck(membersDto);
		
		if(resultCnt > 0)
		{
			map.put("resultCnt", resultCnt);
			return map;
		}
		else{
			membersService.InsertMember(membersDto);
			HttpSession session = request.getSession();
			String PrevPage = (String)session.getAttribute("PrevPage");
			map.put("PrevPage", PrevPage);
			
			return map;
		}
	}	
}
