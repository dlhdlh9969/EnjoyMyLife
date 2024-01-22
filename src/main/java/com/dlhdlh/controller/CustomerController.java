package com.dlhdlh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.CustFavorDto;
import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.service.CustomerService;
import com.dlhdlh.service.MembersService;
import com.github.pagehelper.PageInfo;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	MembersService membersService;
	
	//업체관리 메인페이지 및 검색 기능
	@RequestMapping(value="/dworld/customer")
	public ModelAndView CustomerList(@RequestParam(required=false, defaultValue = "1") int pageNum
			, @RequestParam(required=false, defaultValue = "0")int selectRowNum
			, HttpServletRequest servletRequest
			, CustomerDto customerParam
			, PersetCustDto persetCustParam) throws Exception{
		ModelAndView mv = new ModelAndView("Customer/mainPage");		
		String requestId = null;
		
		if(servletRequest.getSession().getAttribute("userId") != null) {
			requestId = servletRequest.getSession().getAttribute("userId").toString();
			PersetMemberDto getPersetMember = membersService.GetPersetMember(requestId);
			mv.addObject("viewMode", getPersetMember.getViewMode());
		}else {
			mv.addObject("viewMode", "light");
		}
		
		persetCustParam.setUserId(requestId);
		
		
		PersetCustDto getPersetCust = customerService.GetPersetCust(requestId);
		if(getPersetCust == null) {
			customerService.SetNewMember(requestId);
			getPersetCust = customerService.GetPersetCust(requestId);
		}
		
		if(persetCustParam.getMaxrow() != 0) {
			customerService.UpdatePersetCust(persetCustParam);
		}
		
		
		int maxPaging = 5;//페이징 최대 갯수
		int getMaxRow = getPersetCust.getMaxrow(); //페이지당 최대 로우 갯수
		
		//업체명 검색 input이 null로 왔을 경우 where절에 like를 할 수 없으므로 빈값을 넣어줌!
		if(customerParam.getSearchCustNm() == null) {
			customerParam.setSearchCustNm("");
		}
		
		//업체 리스트 조회(검색 기능 포함)
		customerParam.setUserId(requestId);
		PageInfo<CustomerDto> getCustList = new PageInfo<>(customerService.GetCustList(pageNum, getMaxRow, customerParam), maxPaging);
		
		//선택한 업체 정보를 분리하여 디테일 영역에 뿌려줄 데이터
		CustomerDto getSelectCustInfo = new CustomerDto();
		if(getCustList.getSize() != 0) {	
			if(selectRowNum != 0){
				int i = 0;
				getSelectCustInfo = getCustList.getList().get(i);
				
				for(i = 0; selectRowNum < getCustList.getList().get(i).getRowNum(); i++) {
					getSelectCustInfo = getCustList.getList().get(i+1);
				};
			}else {
				getSelectCustInfo = getCustList.getList().get(0);
			}
		}
		getSelectCustInfo.setSearchCustNm(customerParam.getSearchCustNm());
		
		mv.addObject("persetCust", getPersetCust);
		mv.addObject("custList", getCustList);
		mv.addObject("searchCustNm", customerParam.getSearchCustNm());
		mv.addObject("selectCust", getSelectCustInfo);
		return mv;
	}
	
	//업체 관리 수정
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.PUT)
	public String CustomerUpdate(CustomerDto customerParam, HttpServletRequest servletRequest) throws Exception {
		String getCustNm = customerParam.getCustNm().trim();
		customerParam.setCustNm(getCustNm);
		if(getCustNm != "") {
			try {
				String requestId = (String) servletRequest.getSession().getAttribute("userId");
				customerParam.setUpdateUser(requestId);
				customerService.CustomerUpdate(customerParam);
				return "OK";
			} catch (Exception e) {
				e.printStackTrace();
				return "notOK";
			}
		}else {
			return "custNmEmpty";
		}	
	}
	
	//업체 관리 삭제
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.DELETE)
	public String CustomerDelete(CustomerDto customerDto, HttpServletRequest servletRequest) throws Exception{
		try {
			String requestId = (String) servletRequest.getSession().getAttribute("userId");
			customerDto.setDeleteUser(requestId);
			customerService.CustomerDelete(customerDto);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "notOK";
		}
	}
	
	//업체 관리 등록
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.POST)
	public String CustomerInsert(CustomerDto customerParam, HttpServletRequest servletRequest) throws Exception{
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		String getCustNm = customerParam.getCustNm().trim();
		customerParam.setCustNm(getCustNm);
		
		if(getCustNm.equals("")) {
			return "custNmEmpty";
			
		} else{
			try {
				customerParam.setInsertUser(requestId);
				customerParam.setCustNm(getCustNm);
				customerService.CustomerInsert(customerParam);
				return "OK";
			} catch (Exception e) {
				e.printStackTrace();
				return "notOK";
			}
		}
	}
	
	//업체 즐겨찾기
	@ResponseBody
	@RequestMapping(value = "/dworld/customer/custfavor", method = RequestMethod.PUT)
	public String CustFavorToggle(HttpServletRequest servletRequest, CustFavorDto custFavorParam) {
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		custFavorParam.setUserId(requestId);
		try {
			String result = customerService.CustFavorToggle(custFavorParam);
			return result;
		} catch (Exception e) {
			return "notOk";
		}	
	}
	
	// 업무일지 > 모달 > 업체검색 컨트롤
	@ResponseBody
	@RequestMapping(value="/dworld/customer/searchmodal")
	public List<CustomerDto> GetModalCustList(CustomerDto customerParam, HttpServletRequest servletRequest) throws Exception{
		String requestId = servletRequest.getSession().getAttribute("userId").toString();
		customerParam.setUserId(requestId);
		
		List<CustomerDto> getModalCustList = customerService.GetModalCustList(customerParam);
		return getModalCustList;
	}
}

