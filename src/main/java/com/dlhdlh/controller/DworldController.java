package com.dlhdlh.controller;

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
	public String Start() throws Exception{
		return "redirect:/dworld/index";
	}
	
	@RequestMapping("/dworld/index")
	public ModelAndView IndexPage(HttpServletRequest servletRequest)throws Exception{
		ModelAndView mv = new ModelAndView("index");
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto getPersetMember = membersService.GetPersetMember(requestId);
		mv.addObject("viewMode", getPersetMember.getViewMode());
		return mv;
	}
	
	// test페이지
	@RequestMapping("/dworld/test")
	public ModelAndView TestPage(HttpServletRequest servletRequest) throws Exception {
		ModelAndView mv = new ModelAndView("test");
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto getPersetMember = membersService.GetPersetMember(requestId);
		mv.addObject("viewMode", getPersetMember.getViewMode());
		return mv;
	}
	
	@RequestMapping("/dworld/error")
	public String ErrorPage() throws Exception{
		return "error";
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
	
	//ViewMode 전환할때마다 업데이트
	@ResponseBody
	@RequestMapping(value = "/dworld/viewmode", method = RequestMethod.POST)
	public void ViewModeUpdate(HttpServletRequest servletRequest
							, @RequestParam(required=false) String viewMode) throws Exception{
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto persetMemberValues = new PersetMemberDto();
		persetMemberValues.setUserId(requestId);
		persetMemberValues.setViewMode(viewMode);
		if(requestId != null) {
			membersService.UpdatePersetMember(persetMemberValues);
		}
	}
}