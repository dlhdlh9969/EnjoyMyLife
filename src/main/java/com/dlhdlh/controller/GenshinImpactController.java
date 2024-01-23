package com.dlhdlh.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.service.MembersService;

@Controller
public class GenshinImpactController {

	@Autowired
	MembersService membersService;
	
	@RequestMapping( value = "/dworld/genshin")
	public ModelAndView GenshinImpact(HttpServletRequest servletRequest) throws Exception {
		ModelAndView mv = new ModelAndView("GenshinImpact/mainPage");
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto persetMember = membersService.GetPersetMember(requestId);
		mv.addObject("viewMode", persetMember.getViewMode());
		return mv;
	}
}
