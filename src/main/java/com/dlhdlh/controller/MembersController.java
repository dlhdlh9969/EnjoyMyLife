package com.dlhdlh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.service.DworldService;
import com.dlhdlh.service.MembersService;

@Controller
public class MembersController {
	
	@Autowired
	MembersService membersService;
	@Autowired
	DworldService dworldService ;
	
	// 로그인 페이지
	@RequestMapping("/members/login")
	public String LoginPage() {
		return "Members/login";
	}
	
	// 회원가입 페이지
	@RequestMapping("/members/join")
	public String SignInPage() {
		return "Members/join";
	}
	
	// 로그인 submit 프로세스
	@ResponseBody 
	@RequestMapping(value = "/members/loginAjax", method = RequestMethod.POST)
	public String LoginProcessAjax(MembersDto membersParam, HttpServletRequest servletRequest) throws Exception{
		String convertUserPw = dworldService.PasswordSHA256(membersParam.getUserPw());
		membersParam.setUserPw(convertUserPw);
		MembersDto getLoginUser = membersService.LoginMemberInfo(membersParam);
		
		DworldValuesDto dworldValues =  dworldService.DworldValues("SessionInterval");
		String sessionInterval = dworldValues.getValue();
		if(getLoginUser != null) {
			HttpSession session = servletRequest.getSession();
			session.setAttribute("userId", getLoginUser.getUserId());
			session.setAttribute("userName", getLoginUser.getUserName());
			session.setAttribute("auth", getLoginUser.getAuthority());
			session.setMaxInactiveInterval(Integer.parseInt(sessionInterval)); //초 단위 10분=600초
			return "OK";
		}else {
			return "notOK";
		}
	}
	
	// 로그아웃
	@RequestMapping(value="/members/logout") 
	public String logout(HttpServletRequest servletRequest)throws Exception{
		servletRequest.getSession().invalidate();
		return "redirect:/";
	}

	// 회원가입 submit ajax통신
	@ResponseBody 
	@RequestMapping(value = "/member/joinAjax", method = RequestMethod.POST)
	public String JoinProcess(MembersDto membersParam, HttpServletRequest servletRequest) throws Exception{
		String getUserId = membersParam.getUserId().trim();
		String getUserPw = membersParam.getUserPw().trim();
		String getUserName = membersParam.getUserName().trim();		
		
		if(getUserId.isEmpty()) {
			return "userIdEmpty";
		}
		if(getUserPw.isEmpty()) {
			return "userPwEmpty";
		}
		if(getUserName.isEmpty()) {
			return "userNameEmpty";
		}
		try {
			getUserPw = dworldService.PasswordSHA256(getUserPw);
			membersParam.setUserPw(getUserPw);
			membersService.InsertMember(membersParam);
			return "OK"; 
		} catch (Exception e) {
			e.printStackTrace();
 			return "notOK";
		}
	}
	
	// 회원관리 페이지
	@RequestMapping(value = "/dworld/auth/memberscontrol")
	public ModelAndView MembersControl(HttpServletRequest servletRequest, MembersDto membersParam) throws Exception {
		ModelAndView mv = new ModelAndView("Members/members");
		
		String requestId = null;
		
		if(servletRequest.getSession().getAttribute("userId") != null) {
			requestId = servletRequest.getSession().getAttribute("userId").toString();
			PersetMemberDto persetMember = membersService.GetPersetMember(requestId);
			mv.addObject("viewMode", persetMember.getViewMode());
		}else {
			mv.addObject("viewMode", "light");
		}
		
		String getSearchUserId = membersParam.getUserId();
		String getSearchUserName = membersParam.getUserName();
		
		if(membersParam.getUserId() == null) {
			membersParam.setUserId("");
		}
		if(membersParam.getUserName() == null) {
			membersParam.setUserName("");
		}
		List<MembersDto> getUserList = membersService.UserList(membersParam);
		
		mv.addObject("searchUserId", getSearchUserId);
		mv.addObject("searchUserName", getSearchUserName);
		mv.addObject("userList", getUserList);

		return mv;
	}
	
	//회원정보 수정
	@ResponseBody
	@RequestMapping(value = "/dworld/auth/memberscontrol", method = RequestMethod.PUT)
	public String EditMembers(MembersDto membersParam, HttpServletRequest servletRequest) throws Exception{
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		if(membersParam.getAuthority().equals("true")) {
			membersParam.setAuthority("M");
		}else {
			membersParam.setAuthority("G");
		}
		
		String getUserName = membersParam.getUserName().trim();
		String getUserPw = membersParam.getUserPw().trim();
		membersParam.setUserName(getUserName);
		membersParam.setUserPw(getUserPw);
		
		if(getUserName.isEmpty()) {
			return "userIdEmpty";
		}
		if(!getUserPw.isEmpty()) {
			getUserPw = dworldService.PasswordSHA256(getUserPw);
			membersParam.setUserPw(getUserPw);
		}
		try {
			String getUserAuth = membersService.GetUserAuth(requestId);
			if(getUserAuth.equals("A") || getUserAuth.equals("M")) {
				membersService.UpdateMembers(membersParam);
				return "OK";
			}else {
				return "notOK";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "notOK";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/dworld/auth/memberscontrol", method = RequestMethod.DELETE)
	public String DeleteMembers(HttpServletRequest servletRequest, @RequestParam(value = "params") List<String> params ) throws Exception {
		
		System.out.println("params:"+ params);
		return "OK";
	}
}
