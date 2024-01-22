package com.dlhdlh.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.service.DworldService;
import com.dlhdlh.service.MembersService;

@Controller
public class DworldController {
	
	@Autowired
	DworldService dworldService;
	
	@Autowired
	MembersService membersService;
	
	@RequestMapping("/")
	public ModelAndView IndexPage(HttpServletRequest servletRequest) throws Exception{
		ModelAndView mv = null;
		
		if(servletRequest.getSession().getAttribute("userId") == null) {
			mv = new ModelAndView("Members/login.html");
			return mv;
		}else{
			mv = new ModelAndView("index");
			String requestId = null;

			if(servletRequest.getSession().getAttribute("userId") != null) {
				requestId = servletRequest.getSession().getAttribute("userId").toString();
				PersetMemberDto getPersetMember = membersService.GetPersetMember(requestId);
				mv.addObject("viewMode", getPersetMember.getViewMode());
			}else {
				mv.addObject("viewMode", "light");
			}

			return mv;
		}
	}
	
	// test페이지
	@RequestMapping("/test")
	public ModelAndView LoginPage2(HttpServletRequest servletRequest) throws Exception {
		ModelAndView mv = new ModelAndView("test");
		String requestId = null;

		if(servletRequest.getSession().getAttribute("userId") != null) {
			requestId = servletRequest.getSession().getAttribute("userId").toString();
			PersetMemberDto getPersetMember = membersService.GetPersetMember(requestId);
			mv.addObject("viewMode", getPersetMember.getViewMode());
		}else {
			mv.addObject("viewMode", "light");
		}

		return mv;
	}
	
	@RequestMapping("/dworld/error")
	public String ErrorPage() throws Exception{
		return "error";
	}
	
	//현재 페이지를 저장하여 이전 페이지로 돌아가기 위한 경로에 사용
	public String GetPrevPage(HttpServletRequest servletRequest) {
		StringBuilder getStringBuilder = new StringBuilder(servletRequest.getRequestURI().toString());
	    String getRequestURL = getStringBuilder.toString();  //현재 URL 문자로 변환
	    String result = getRequestURL+"?";
	    Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = servletRequest.getParameter(paramName);
            result = result+paramName+"="+paramValue+"&";
        }
		return result;
	}
	
	// 현재 페이지 주소 저장
	public void SetPrevPage(HttpServletRequest servletRequest) throws Exception {
		String getPrevPage = GetPrevPage(servletRequest);
		servletRequest.getSession().setAttribute("prevPage", getPrevPage);
	}
	
	// 이전 페이지 주소 조회
// prevPage 저장 방식을 session으로 변경함 2024.01.21 김동환
//	public String GetPrevPage(HttpServletRequest servletRequest, String menu) throws Exception {
//		String userId = servletRequest.getSession().getAttribute("userId").toString();
//		PrevPageDto prevPageDto = new PrevPageDto();
//		prevPageDto.setUserId(userId);
//		prevPageDto.setMenu(menu);
//		String prevPage = dworldService.SelectPrevPage(prevPageDto);
//		return prevPage;
//	}
	
	//ViewMode 컨트롤
	@ResponseBody
	@RequestMapping(value = "/dworld/viewmode", method = RequestMethod.POST)
	public void ViewModeUpdate(HttpServletRequest servletRequest, @RequestParam(required=false) String viewMode) throws Exception{
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto persetMemberParam = new PersetMemberDto();
		persetMemberParam.setUserId(requestId);
		persetMemberParam.setViewMode(viewMode);
		try {
			if(requestId != null) {
				membersService.InsertPersetMember(persetMemberParam);
				
			}
		} catch (Exception e) {
			membersService.UpdatePersetMember(persetMemberParam);
		}
	}
}