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
		MembersDto loginUserInfo = membersService.GetLoginMemberInfo(membersParam);
		
		DworldValuesDto dworldValues =  dworldService.GetDworldValues("SessionInterval");
		String sessionInterval = dworldValues.getValue();
		if(loginUserInfo != null) {
			HttpSession session = servletRequest.getSession();
			session.setAttribute("userId", loginUserInfo.getUserId());
			session.setAttribute("userName", loginUserInfo.getUserName());
			session.setAttribute("auth", loginUserInfo.getAuthority());
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
		String userId = membersParam.getUserId().trim();
		String userPw = membersParam.getUserPw().trim();
		String userName = membersParam.getUserName().trim();		
		
		if(userId.isEmpty()) {
			return "userIdEmpty";
		}
		if(userPw.isEmpty()) {
			return "userPwEmpty";
		}
		if(userName.isEmpty()) {
			return "userNameEmpty";
		}
		try {
			userPw = dworldService.PasswordSHA256(userPw);
			membersParam.setUserPw(userPw);
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
		
		String searchUserId = membersParam.getUserId();
		String searchUserName = membersParam.getUserName();
		
		if(membersParam.getUserId() == null) {
			membersParam.setUserId("");
		}
		if(membersParam.getUserName() == null) {
			membersParam.setUserName("");
		}
		List<MembersDto> userList = membersService.UserList(membersParam);
		
		mv.addObject("searchUserId", searchUserId);
		mv.addObject("searchUserName", searchUserName);
		mv.addObject("userList", userList);

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
		
		String userName = membersParam.getUserName().trim();
		String userPw = membersParam.getUserPw().trim();
		membersParam.setUserName(userName);
		membersParam.setUserPw(userPw);
		
		if(userName.isEmpty()) {
			return "userIdEmpty";
		}
		if(!userPw.isEmpty()) {
			userPw = dworldService.PasswordSHA256(userPw);
			membersParam.setUserPw(userPw);
		}
		
		int resultCount = 0;
		try {
			String userAuth = membersService.GetUserAuth(requestId);
			if(userAuth.equals("A") || userAuth.equals("M")) {
				resultCount += membersService.UpdateMembers(membersParam);
				if(resultCount > 0) {
					return "OK";				
				}else {
					return "notOK";
				}
			}else {
				return "notOK";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "notOK";
		}
	}
	
	
	// 계정관리 다중삭제 기능 ajax
	@ResponseBody
	@RequestMapping(value = "/dworld/auth/memberscontrol", method = RequestMethod.DELETE)
	public int DeleteMembers(HttpServletRequest servletRequest,@RequestParam(value ="userIds[]" ) List<String> userIds ) throws Exception {
		int resultCount = 0;
		for( int i = 0 ; i< userIds.size() ; i++ ) {
			String userId = userIds.get(i);
			resultCount += membersService.DeleteMember(userId); 
		}
		return resultCount;
	}
}
