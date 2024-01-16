package com.dlhdlh.controller;

import java.time.LocalDate;
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
import com.dlhdlh.service.DworldService;
import com.dlhdlh.service.WorkLogService;
import com.github.pagehelper.PageInfo;

@RestController
public class WorkLogController {
	
	@Autowired
	WorkLogService worklogService;
	
	@Autowired
	DworldService dworldService;
	
	@Autowired
	DworldController dworldController;

	//업무일지 리스트
	@RequestMapping(value="/dworld/worklog")
	public ModelAndView WorkLogList(@RequestParam(required=false, defaultValue = "1") int pageNum
			, HttpServletRequest servletRequest
			, WorkLogDto worklogParam
			, @RequestParam(required=false, defaultValue = "0") int entrance) throws Exception {
		ModelAndView mv = new ModelAndView("WorkLog/MainPage");
		String requestUserId = (String) servletRequest.getSession().getAttribute("userId");

		//검색 시작-종료일 초기값
		LocalDate startDt; 
		LocalDate endDt;
		if(worklogParam.getStartDt() == null) {
			startDt = LocalDate.now().minusMonths(1);
		}else {
			startDt = LocalDate.parse(worklogParam.getStartDt());
		}
		worklogParam.setStartDt(startDt.toString());
		
		if(worklogParam.getEndDt() == null) {
			endDt = LocalDate.now();
		}else {
			endDt = LocalDate.parse(worklogParam.getEndDt());
		}
		worklogParam.setEndDt(endDt.toString());

		//완료 여부 컨트롤
		if(worklogParam.getComplYn() == null) {
			worklogParam.setComplYn("A");
		}else {
			if(worklogParam.getComplYn().equals("Y")) {
			}else {
				if(worklogParam.getComplYn().equals("N")) {
				}else {
					worklogParam.setComplYn("A");
				}
			}
		}
		
		//order by 컬럼명 컨트롤
		if(worklogParam.getOrder1() == null) {
			worklogParam.setOrder1("idx");
		}else {
			if(worklogParam.getOrder1().equals("cust_nm")) {
				worklogParam.setOrder1("cust_nm");
			}else {
				if(worklogParam.getOrder1().equals("receipt_dt")) {
					worklogParam.setOrder1("receipt_dt");
				}else {
					worklogParam.setOrder1("idx");
				}
			}
		}
		
		// 내림, 오름차순 컨트롤
		if(worklogParam.getOrder2() == null) {
			worklogParam.setOrder2("desc");
		}else {
			if(worklogParam.getOrder2().equals("asc")) {
				worklogParam.setOrder2("asc");
			}else {
				worklogParam.setOrder2("desc");
			}
		}
		
		//업체명 검색 컨트롤
		if(worklogParam.getCustNm() == null) {
			worklogParam.setCustNm("");
		}

		//제목 검색 컨트롤
		if(worklogParam.getTitle() == null) {
			worklogParam.setTitle("");
		}
		
		//내용 검색 컨트롤
		if(worklogParam.getContent() == null) {
			worklogParam.setContent("");
		}
		
		// maxrow 컨트롤
		if(worklogParam.getMaxrow() == 0) {
			worklogParam.setMaxrow(10);
		}
		
		// 개인별 게시판 설정값 조회
		PersetWorkLogDto persetWorkLog = worklogService.GetPersetWorkLog(requestUserId);
		if(persetWorkLog == null) {
			worklogService.SetNewMember(requestUserId);
			persetWorkLog = worklogService.GetPersetWorkLog(requestUserId);
			persetWorkLog.setComplYn("A");
			persetWorkLog.setCustNm("");
			persetWorkLog.setEndDt(endDt.toString());
			persetWorkLog.setStartDt(startDt.toString());
			persetWorkLog.setOrder1("idx");
			persetWorkLog.setOrder2("desc");
			persetWorkLog.setTitle("");
			persetWorkLog.setContent("");
		}
		
		// 개인별 게시판 설정값 업데이트 여부
		if(entrance == 1) {
			worklogParam.setTitle(persetWorkLog.getTitle());
			worklogParam.setContent(persetWorkLog.getContent());
			worklogParam.setCustNm(persetWorkLog.getCustNm());
			worklogParam.setStartDt(persetWorkLog.getStartDt());
			worklogParam.setEndDt(persetWorkLog.getEndDt());
			worklogParam.setOrder1(persetWorkLog.getOrder1());
			worklogParam.setOrder2(persetWorkLog.getOrder2());
			worklogParam.setComplYn(persetWorkLog.getComplYn());
			}else if(entrance == 0) {
				persetWorkLog.setTitle(worklogParam.getTitle());
				persetWorkLog.setContent(worklogParam.getContent());
				persetWorkLog.setCustNm(worklogParam.getCustNm());
				persetWorkLog.setStartDt(worklogParam.getStartDt());
				persetWorkLog.setEndDt(worklogParam.getEndDt());
				persetWorkLog.setOrder1(worklogParam.getOrder1());
				persetWorkLog.setOrder2(worklogParam.getOrder2());
				persetWorkLog.setComplYn(worklogParam.getComplYn());
				persetWorkLog.setMaxrow(worklogParam.getMaxrow());
				worklogService.UpdatePersetWorkLog(persetWorkLog);
		}

		// 업무일지 리스트 
		int maxPaging = 8;//페이징 최대 갯수
		int maxRow = persetWorkLog.getMaxrow(); //페이지당 최대 로우 갯수
		worklogParam.setUserId(requestUserId);
		LocalDate endDtplus1d = endDt.plusDays(1);
		worklogParam.setEndDt(endDtplus1d.toString());
		if(worklogParam.getComplYn().equals("A")) {
			worklogParam.setComplYn("");
		}
		PageInfo<WorkLogDto> workLogList = new PageInfo<>(worklogService.GetWorkLogList(pageNum, maxRow, worklogParam), maxPaging);
		
		
		// 현재 페이지 주소 저장
		dworldController.SetPrevPage(servletRequest, "WorkLog");
		
		mv.addObject("PersetWorkLog", persetWorkLog);
		mv.addObject("WorkLogList", workLogList);
		return mv;		
	}
	
	//디테일 페이지 컨트롤
	@RequestMapping(value = "/dworld/worklog/detail")
	public ModelAndView WorkLogWrite(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception{
		ModelAndView mv = new ModelAndView("WorkLog/DetailPage");
		String requestUserId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestUserId);
		WorkLogDto worklogDetail= worklogService.GetWorkLogDetail(worklogParam);
		mv.addObject("WorkLogDetail",worklogDetail);
		//신규 등록과 기존 디테일과 구분
		if(worklogParam.getIdx() == 0) {
			mv.addObject("MODE", "new");
		}else {
			mv.addObject("MODE", "detail");	
		}
		
		//이전페이지 주소 조회
		String prevPage = dworldController.GetPrevPage(servletRequest, "WorkLog");
		
		mv.addObject("prevPage", prevPage);	
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
	public String InsertWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception {

		String requestUserId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestUserId);
		
		String getReceiptDt = worklogParam.getReceiptDt();
		String getDueDt = worklogParam.getDueDt();
		String getComplDt = worklogParam.getComplDt();

		if(getReceiptDt.equals("")) {
			worklogParam.setReceiptDt(null);
		}
		if(getDueDt.equals("")) {
			worklogParam.setDueDt(null);
		} 
		if(getComplDt.equals("")) {
			worklogParam.setComplDt(null);
		}
		try {
			worklogService.InsertWorkLog(worklogParam);
			return "OK";
		} catch (Exception e) {
			return "notOK";
		}
	}
	
	//업무일지 삭제
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.DELETE)
	public String DeleteWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception {

		String requestUserId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestUserId);
		try {
			worklogService.DeleteWorkLog(worklogParam);
			return "OK";
		} catch (Exception e) {
			return "notOK";
		}
	}
	
	//업무일지 수정
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.PUT)
	public String UpdateWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception {
		
		String requestUserId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestUserId);

		if(worklogParam.getReceiptDt().equals("")) {
			worklogParam.setReceiptDt(null);
		}
		if(worklogParam.getDueDt().equals("")) {
			worklogParam.setDueDt(null);
		} 
		if(worklogParam.getComplDt().equals("")) {
			worklogParam.setComplDt(null);
		}		

		try {
			worklogService.UpdateWorkLog(worklogParam);
			return "OK";
		} catch (Exception e) {
			return "notOK";
		}
	}
}
