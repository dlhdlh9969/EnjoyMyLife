package com.dlhdlh.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.dto.PersetWorkLogDto;
import com.dlhdlh.dto.WorkLogDto;
import com.dlhdlh.service.CustomerService;
import com.dlhdlh.service.DworldService;
import com.dlhdlh.service.MembersService;
import com.dlhdlh.service.WorkLogService;
import com.github.pagehelper.PageInfo;

@RestController
public class WorkLogController {
	
	@Autowired
	WorkLogService worklogService;
	
	@Autowired
	DworldService dworldService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	MembersService membersService;
	
	@Autowired
	DworldController dworldController;

	//업무일지 리스트
	@RequestMapping(value="/dworld/worklog")
	public ModelAndView WorkLogList(@RequestParam(required=false, defaultValue = "1") int pageNum
								, HttpServletRequest servletRequest
								, WorkLogDto worklogParam
								, @RequestParam(required=false, defaultValue = "0") int entrance) throws Exception {
		ModelAndView mv = new ModelAndView("WorkLog/MainPage");
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto persetMember = membersService.GetPersetMember(requestId);
		mv.addObject("viewMode", persetMember.getViewMode());
		
		//검색 시작-종료일 초기값
		LocalDate endDt = null;
		if(worklogParam.getStartDt() == null) {
			worklogParam.setStartDt("");
		}else {
			worklogParam.setStartDt(worklogParam.getStartDt());
		}
		
		if(worklogParam.getEndDt() == null) {
			worklogParam.setEndDt("");
		}else {
			worklogParam.setEndDt(worklogParam.getEndDt());
			if(!worklogParam.getEndDt().isEmpty()) {
				endDt = LocalDate.parse(worklogParam.getEndDt()).plusDays(1);
			}
		}

		//완료 여부 컨트롤
		if(worklogParam.getComplYn() == null) {
			worklogParam.setComplYn("N");
		}
		
		//order by 컬럼명 컨트롤
		if(worklogParam.getOrder1() == null) {
			worklogParam.setOrder1("idx");
		}
		
		// 내림, 오름차순 컨트롤
		if(worklogParam.getOrder2() == null) {
			worklogParam.setOrder2("desc");
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
		//문서 구분 검색 컨트롤
		if(worklogParam.getDocumentType() == null) {
			worklogParam.setDocumentType("");
		}
		//일자 검색 대상 구분 컨트롤
		if(worklogParam.getOrderby1() == null) {
			worklogParam.setOrderby1("insert_dt");
		}
		
		// maxrow 컨트롤
		if(worklogParam.getMaxrow() == 0) {
			worklogParam.setMaxrow(10);
		}
		
		// 개인별 게시판 설정값 조회
		PersetWorkLogDto persetWorkLog = worklogService.GetPersetWorkLog(requestId);
		if(persetWorkLog == null) {
			worklogService.SetNewMember(requestId);
			persetWorkLog = worklogService.GetPersetWorkLog(requestId);
		}
		
		// 개인별 게시판 설정값 업데이트 여부
		if(entrance == 1) {
			worklogParam.setTitle(persetWorkLog.getTitle());
			worklogParam.setContent(persetWorkLog.getContent());
			worklogParam.setDocumentType(persetWorkLog.getDocumentType());
			worklogParam.setCustNm(persetWorkLog.getCustNm());
			worklogParam.setStartDt(persetWorkLog.getStartDt());
			worklogParam.setEndDt(persetWorkLog.getEndDt());
			worklogParam.setOrder1(persetWorkLog.getOrder1());
			worklogParam.setOrder2(persetWorkLog.getOrder2());
			worklogParam.setOrderby1(persetWorkLog.getOrderby1());
			worklogParam.setComplYn(persetWorkLog.getComplYn());
			}else {
				persetWorkLog.setTitle(worklogParam.getTitle());
				persetWorkLog.setContent(worklogParam.getContent());
				persetWorkLog.setDocumentType(worklogParam.getDocumentType());
				persetWorkLog.setCustNm(worklogParam.getCustNm());
				persetWorkLog.setStartDt(worklogParam.getStartDt());
				persetWorkLog.setEndDt(worklogParam.getEndDt());
				persetWorkLog.setOrder1(worklogParam.getOrder1());
				persetWorkLog.setOrder2(worklogParam.getOrder2());
				persetWorkLog.setOrderby1(worklogParam.getOrderby1());
				persetWorkLog.setComplYn(worklogParam.getComplYn());
				persetWorkLog.setMaxrow(worklogParam.getMaxrow());
				worklogService.UpdatePersetWorkLog(persetWorkLog);
			}

		// 업무일지 리스트 
		int maxPaging = 5;//페이징 최대 갯수
		int maxRow = persetWorkLog.getMaxrow(); //페이지당 최대 로우 갯수
		worklogParam.setUserId(requestId);
		
		// persetWorkLog 저장되고나서 DB에 보낼 값으로 변환
		if(worklogParam.getComplYn().equals("A")) {
			worklogParam.setComplYn("");
		}
		if(worklogParam.getStartDt().isEmpty()) {
			worklogParam.setStartDt("0001-01-01");
		}
		if(worklogParam.getEndDt().isEmpty()) {
			worklogParam.setEndDt("9999-01-01");
		}else {
			worklogParam.setEndDt(endDt.toString());
		}
		
		PageInfo<WorkLogDto> workLogList = new PageInfo<>(worklogService.GetWorkLogList(pageNum, maxRow, worklogParam), maxPaging);
		
		// 업무일지 문서 종류 구분값
		List<DworldValuesDto> documentType = dworldService.ListDworldValues("documentType");
		
		// 현재 페이지 주소 저장
		dworldService.SetPrevPage(servletRequest);
		
		// Dday 안전일 기준값
		List<DworldValuesDto> dDayValues = dworldService.ListDworldValues("Dday");
		
		HashMap<String, Integer> convertDday= new HashMap<String, Integer>();
		convertDday.put("danger", Integer.parseInt(dDayValues.get(0).getValue())); //위험
		convertDday.put("safe", Integer.parseInt(dDayValues.get(1).getValue())); //주의
		
				
		// prevPage 저장 방식을 session으로 변경함 2024.01.21 김동환
		// dworldController.SetPrevPage(servletRequest, "WorkLog");
		mv.addObject("persetWorkLog", persetWorkLog);
		mv.addObject("documentType", documentType);
		mv.addObject("dDayValues", convertDday);
		mv.addObject("workLogList", workLogList);
		return mv;		
	}
	
	//디테일 페이지 컨트롤
	@RequestMapping(value = "/dworld/worklog/detail")
	public ModelAndView WorkLogWrite(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception{
		ModelAndView mv = new ModelAndView("WorkLog/DetailPage");
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		PersetMemberDto persetMember = membersService.GetPersetMember(requestId);
		mv.addObject("viewMode", persetMember.getViewMode());
		
		worklogParam.setUserId(requestId);
		WorkLogDto worklogDetail= worklogService.GetWorkLogDetail(worklogParam);
		
		//신규 등록과 기존 디테일과 구분
		if(worklogDetail == null) {
			mv.addObject("MODE", "new");
		}else {
			mv.addObject("MODE", "detail");	
		}
		
		// 업무일지 문서 종류 구분값
		List<DworldValuesDto> documentType = dworldService.ListDworldValues("documentType");
		// Dday 안전일 기준값
		List<DworldValuesDto> dDayValues = dworldService.ListDworldValues("Dday");
		HashMap<String, Integer> convertDday= new HashMap<String, Integer>();
		convertDday.put("1", Integer.parseInt(dDayValues.get(0).getValue())); 
		convertDday.put("2", Integer.parseInt(dDayValues.get(1).getValue())); 
		
		//이전페이지 주소 조회
		String prevPage = servletRequest.getSession().getAttribute("prevPage").toString();
		
		mv.addObject("workLogDetail",worklogDetail);
		mv.addObject("documentType", documentType);
		mv.addObject("dDayValues", convertDday);
		mv.addObject("prevPage", prevPage);	
		return mv;
	}
	
	//업무일지 등록
	@ResponseBody
	@RequestMapping(value="/dworld/worklog/control", method = RequestMethod.POST)
	public String InsertWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception {

		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setInsertUser(requestId);
		System.out.println(worklogParam);
		String receiptDt = worklogParam.getReceiptDt();
		String dueDt = worklogParam.getDueDt();
		String complDt = worklogParam.getComplDt();

		if(receiptDt.equals("")) {
			worklogParam.setReceiptDt(null);
		}
		if(dueDt.equals("")) {
			worklogParam.setDueDt(null);
		} 
		if(complDt.equals("")) {
			worklogParam.setComplDt(null);
		}
		try {
			int result = worklogService.InsertWorkLog(worklogParam);
			return "OK";
		} catch (Exception e) {
			return "notOK";
		}
	}
	
	//업무일지 삭제
	@RequestMapping(value = "/dworld/worklog/control", method = RequestMethod.DELETE)
	public String DeleteWorkLog(HttpServletRequest servletRequest, WorkLogDto worklogParam) throws Exception {

		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestId);
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
		
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		worklogParam.setUserId(requestId);

		if(worklogParam.getReceiptDt().equals("")) {
			worklogParam.setReceiptDt(null);
		}
		if(worklogParam.getDueDt().equals("")) {
			worklogParam.setDueDt(null);
		} 
		if(worklogParam.getComplDt().equals("")) {
			worklogParam.setComplDt(null);
		}
		if(worklogParam.getDocumentType() == null) {
			worklogParam.setDocumentType("");
		}

		try {
			worklogService.UpdateWorkLog(worklogParam);
			return "OK";
		} catch (Exception e) {
			return "notOK";
		}
	}
}
