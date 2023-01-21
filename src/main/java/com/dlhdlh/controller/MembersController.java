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
	private MembersService membersService;
	
	
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
	public Object LoginProcessAjax(MembersDto loginRequest, HttpServletRequest request) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		int resultCnt = membersService.loginCheck(loginRequest);

		map.put("resultCnt", resultCnt);
		
		if(resultCnt > 0)
		{
			MembersDto memberDto = membersService.selectMemberDto(loginRequest.getUserId());
			HttpSession session = request.getSession();
			session.setAttribute("userId", memberDto.getUserId());
			session.setAttribute("userName", memberDto.getUserName());
			session.setMaxInactiveInterval(600);
			
//			String prevPage = request.getHeader("prevPage");
//			if(prevPage != null) {
//				map.put("prevPage", prevPage);
//			}
			
			return map;
			
		}
		else {
			return map;
		}
	}
	
	@RequestMapping(value="members/logout") // 로그아웃
	public String logout(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String referer = request.getHeader("Referer");
		session.invalidate();
		return "redirect:" + referer;
	}

	@ResponseBody // 회원가입 submit 프로세스
	@RequestMapping(value = "member/signInAjax", method = RequestMethod.POST)
	public Object signInProcess(MembersDto memberInfo, HttpServletRequest request) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		int resultCnt = membersService.userIdCheck(memberInfo);
		
		if(resultCnt > 0)
		{
			map.put("resultCnt", resultCnt);
			return map;
	 
		}
		else {
			membersService.insertMember(memberInfo);
			HttpSession session = request.getSession();
			String PrevPage = (String)session.getAttribute("PrevPage");
			map.put("PrevPage", PrevPage);
			
			return map;
		}
	}
	
}
