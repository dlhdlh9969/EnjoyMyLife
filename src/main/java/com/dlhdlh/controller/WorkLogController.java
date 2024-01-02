package com.dlhdlh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.WorkLogDto;
import com.dlhdlh.service.WorkLogService;
import com.github.pagehelper.PageInfo;

@Controller
public class WorkLogController {
	
	private static final String String = null;
	@Autowired
	WorkLogService worklogService;
	
	@RequestMapping(value="/dworld/worklog")
	public ModelAndView WorkLogList(@RequestParam(required=false, defaultValue = "1") int pageNum, HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {
		ModelAndView mv = new ModelAndView("WorkLog/Mainpage");
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		int maxPaging = 10;//페이징 최대 갯수
		int maxRow = 5; //페이지당 최대 로우 갯수
		worklogDto.setUserId(userId);
		
		//완료 여부 컨트롤
		if(worklogDto.getComplYn() == null) {
			worklogDto.setComplYn("");
			mv.addObject("ResponseComplYn","A");
		}else if(worklogDto.getComplYn().equals("A")) {
			worklogDto.setComplYn("");
			mv.addObject("ResponseComplYn","A");
		}else if(worklogDto.getComplYn().equals("Y")) {
			mv.addObject("ResponseComplYn","Y");
		}else if(worklogDto.getComplYn().equals("N")) {
			mv.addObject("ResponseComplYn","N");
		}
		
		//order by 컬럼명 컨트롤
		if(worklogDto.getOrder1() == null) {
			worklogDto.setOrder1("idx");
			mv.addObject("order1","idx");
		}else if(worklogDto.getOrder1().equals("idx")) {
			mv.addObject("order1","idx");
		}else if(worklogDto.getOrder1().equals("cust_nm")) {
			mv.addObject("order1","cust_nm");
		}else if(worklogDto.getOrder1().equals("receipt_dt")) {
			mv.addObject("order1","receipt_dt");
		}else if(worklogDto.getOrder1().equals("due_dt")) {
			mv.addObject("order1","due_dt");
		}
		
		// 내림, 오름차순 컨트롤
		if(worklogDto.getOrder2() == null) {
			worklogDto.setOrder2("desc");
			mv.addObject("order2","desc");
		}else if(worklogDto.getOrder2().equals("desc")) {
			mv.addObject("order2","desc");
		}else if(worklogDto.getOrder2().equals("asc")) {
			mv.addObject("order2","asc");
		}
		
		
		System.out.println("custnm : "+worklogDto.getCustNm());
		System.out.println("title : "+worklogDto.getTitle());
		//업체명 검색 컨트롤
		if(worklogDto.getCustNm() == null) {
			worklogDto.setCustNm("");
			mv.addObject("SearchCustNm", "");
		}else {
			mv.addObject("SearchCustNm", worklogDto.getCustNm());
		}
		
		//제목 검색 컨트롤
		if(worklogDto.getTitle() == null) {
			worklogDto.setTitle("");	
			mv.addObject("SearchTitle", "");
		}else {
			mv.addObject("SearchTitle", worklogDto.getTitle());
		}
			
		PageInfo<WorkLogDto> workLogList = new PageInfo<>(worklogService.GetWorkLogList(pageNum, maxRow, worklogDto), maxPaging);
		
		mv.addObject("WorkLogList", workLogList);
		return mv;	
	}
	
	@ResponseBody
	@RequestMapping(value = "/dworld/worklog/write")
	public ModelAndView WorkLogWrite(HttpServletRequest servletRequest) throws Exception{
		ModelAndView mv = new ModelAndView("WorkLog/WritePage");
		
		return mv;
	}

}
