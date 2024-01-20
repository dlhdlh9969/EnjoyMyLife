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
import com.dlhdlh.dto.PrevPageDto;
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
			mv = new ModelAndView("Members/login");
			return mv;
		}else{
			mv = new ModelAndView("index");
			String requestId = null;

			if(servletRequest.getSession().getAttribute("userId") != null) {
				requestId = servletRequest.getSession().getAttribute("userId").toString();
				PersetMemberDto persetMember = membersService.GetPersetMember(requestId);
				mv.addObject("viewMode", persetMember.getViewMode());
			}else {
				mv.addObject("viewMode", "light");
			}

			return mv;
		}
	}
	
	@RequestMapping("/dworld/error")
	public String ErrorPage() throws Exception{
		return "error";
	}
	
	//현재 페이지를 저장하여 이전 페이지로 돌아가기 위한 경로에 사용
	public String PrevPage(HttpServletRequest servletRequest) {
		StringBuilder stringBuilder = new StringBuilder(servletRequest.getRequestURI().toString());
	    String requestURL = stringBuilder.toString();  //현재 URL 문자로 변환
	    String result = requestURL+"?";
	    Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = servletRequest.getParameter(paramName);
            result = result+paramName+"="+paramValue+"&";
        }
		return result;
	}
	
	// 현재 페이지 주소 저장
	public void SetPrevPage(HttpServletRequest servletRequest, String menu) throws Exception {
		String url = PrevPage(servletRequest);
		String userId = servletRequest.getSession().getAttribute("userId").toString();
		PrevPageDto prevPageDto = new PrevPageDto();
		prevPageDto.setUserId(userId);
		prevPageDto.setUrl(url);
		prevPageDto.setMenu(menu);
		
		try {
			dworldService.InsertPrevUrl(prevPageDto);
		} catch (Exception e) {
			dworldService.UpdatePrevUrl(prevPageDto);
		}
	}
	
	// 이전 페이지 주소 조회
	public String GetPrevPage(HttpServletRequest servletRequest, String menu) throws Exception {
		String userId = servletRequest.getSession().getAttribute("userId").toString();
		PrevPageDto prevPageDto = new PrevPageDto();
		prevPageDto.setUserId(userId);
		prevPageDto.setMenu(menu);
		String prevPage = dworldService.SelectPrevPage(prevPageDto);
		return prevPage;
	}
	
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