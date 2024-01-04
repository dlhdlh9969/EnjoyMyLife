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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.WorkLogDto;
import com.dlhdlh.service.WorkLogService;
import com.github.pagehelper.PageInfo;

@RestController
@Controller
public class WorkLogController {
	
	@Autowired
	WorkLogService worklogService;

	@RequestMapping(value="/dworld/worklog")
	public ModelAndView WorkLogList(@RequestParam(required=false, defaultValue = "1") int pageNum, HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {
		ModelAndView mv = new ModelAndView("WorkLog/Mainpage");
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		int maxPaging = 10;//페이징 최대 갯수
		int maxRow = 10; //페이지당 최대 로우 갯수
		
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
		ModelAndView error = new ModelAndView("error");
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		worklogDto.setUserId(userId);
		WorkLogDto worklogDetail= worklogService.GetWorkLogDetail(worklogDto);
//		try {
//			 
//		} catch (Exception e) {
//			e.printStackTrace();
//			return error;
//		} 
		
		//신규 등록과 기존 디테일과 구분
		if(worklogDto.getIdx() == 0) {
			mv.addObject("MODE", "new");
		}else {
			mv.addObject("MODE", "detail");	
		}
		
		mv.addObject("WorkLogDetail",worklogDetail);		
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
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		worklogDto.setUserId(userId);
		
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
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		worklogDto.setUserId(userId);
		
		worklogService.DeleteWorkLog(worklogDto.getIdx());
	}
	
	//업무일지 수정
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.PUT)
	public void UpdateWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogDto) throws Exception {
		HttpSession session = servletRequest.getSession();
		String userId = (String) session.getAttribute("userId");
		worklogDto.setUserId(userId);
		
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
	
		worklogService.UpdateWorkLog(worklogDto);
	}


}
