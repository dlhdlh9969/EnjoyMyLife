package com.dlhdlh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetWorkLogDto;
import com.dlhdlh.dto.WorkLogDto;
import com.dlhdlh.service.WorkLogService;
import com.github.pagehelper.PageInfo;

@RestController
public class WorkLogController {
	
	@Autowired
	WorkLogService worklogService;

	@RequestMapping(value="/dworld/worklog")
	public ModelAndView WorkLogList(@RequestParam(required=false, defaultValue = "1") int pageNum
			, HttpServletRequest servletRequest
			, WorkLogDto worklogDto
			, PersetWorkLogDto persetWorkLogDto) throws Exception {
		ModelAndView mv = new ModelAndView("WorkLog/MainPage");
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		persetWorkLogDto.setUserId(requestId);
		
		if(persetWorkLogDto.getMaxrow() != 0) {
			worklogService.UpdatePersetWorkLog(persetWorkLogDto);
		}
		
		PersetWorkLogDto persetWorkLog = worklogService.GetPersetWorkLog(requestId);
		mv.addObject("PersetWorkLog", persetWorkLog);
		
		int maxPaging = 10;//페이징 최대 갯수
		int maxRow = persetWorkLog.getMaxrow(); //페이지당 최대 로우 갯수
		
		worklogDto.setUserId(requestId);
		
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
		
		//order3 컨트롤
		if(worklogDto.getOrder2().equals("desc")) {
			worklogDto.setOrder3("asc");
		}else {
			worklogDto.setOrder3("desc");
		}
		
		PageInfo<WorkLogDto> workLogList = new PageInfo<>(worklogService.GetWorkLogList(pageNum, maxRow, worklogDto), maxPaging);
		mv.addObject("WorkLogList", workLogList);
		
		return mv;
		
		
	}
	
	//디테일 페이지 컨트롤
	@RequestMapping(value = "/dworld/worklog/detail")
	public ModelAndView WorkLogWrite(HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception{
		ModelAndView mv = new ModelAndView("WorkLog/DetailPage");
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogDto.setUserId(requestId);
		WorkLogDto worklogDetail= worklogService.GetWorkLogDetail(worklogDto);
		mv.addObject("WorkLogDetail",worklogDetail);
		//신규 등록과 기존 디테일과 구분
		if(worklogDto.getIdx() == 0) {
			mv.addObject("MODE", "new");
		}else {
			mv.addObject("MODE", "detail");	
		}
		
			return mv;
	}
	
	// 업체검색 모달 컨트롤
	@ResponseBody
	@RequestMapping(value="/dworld/worklog/searchCust")
	public List<CustomerDto> CustList(@RequestParam(required=false, defaultValue = "") String searchCustNm) throws Exception{
		return worklogService.GetCustList(searchCustNm);
	}
	
	//업무일지 등록
	@ResponseBody
	@RequestMapping(value="/dworld/worklog/control", method = RequestMethod.POST)
	public void InsertWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {

		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogDto.setUserId(requestId);
		
		String getReceiptDt = worklogDto.getReceiptDt();
		String getDueDt = worklogDto.getDueDt();
		String getComplDt = worklogDto.getComplDt();

		if(getReceiptDt.equals("")) {
			worklogDto.setReceiptDt(null);
		}
		if(getDueDt.equals("")) {
			worklogDto.setDueDt(null);
		} 
		if(getComplDt.equals("")) {
			worklogDto.setComplDt(null);
		}
		
		worklogService.InsertWorkLog(worklogDto);
	}
	
	//업무일지 삭제
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.DELETE)
	public void DeleteWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {

		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogDto.setUserId(requestId);
		
		worklogService.DeleteWorkLog(worklogDto.getIdx());
	}
	
	//업무일지 수정
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.PUT)
	public String UpdateWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {
		
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		
		worklogDto.setUserId(requestId);
		String getReceiptDt = worklogDto.getReceiptDt();
		String getDueDt = worklogDto.getDueDt();
		String getComplDt = worklogDto.getComplDt();
		
		if(getReceiptDt.equals("")) {
			worklogDto.setReceiptDt(null);
		}
		if(getDueDt.equals("")) {
			worklogDto.setDueDt(null);
		} 
		if(getComplDt.equals("")) {
			worklogDto.setComplDt(null);
		}
		

		String idCheck = "N";
		try {
			worklogService.UpdateWorkLog(worklogDto);
			idCheck = "Y";
		} catch (Exception e) {
			return "redirect:/error";
		}
		
		return idCheck;
		
	}


}
