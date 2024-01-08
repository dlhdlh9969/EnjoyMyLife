package com.dlhdlh.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.dlhdlh.service.CustomerService;
import com.github.pagehelper.PageInfo;

@Controller
public class CustomerController {
	
	@Autowired
	public CustomerService customerService;
	
	//업체관리 메인페이지 및 검색 기능
	@RequestMapping(value="/dworld/customer")
	public ModelAndView CustomerList(@RequestParam(required=false, defaultValue = "1") int pageNum
			, @RequestParam(required=false, defaultValue = "0")int selectRowNum
			, HttpServletRequest servletRequest
			, CustomerDto customerDto
			, PersetCustDto persetCustDto) throws Exception{
		ModelAndView mv = new ModelAndView("Customer/MainPage");		
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		persetCustDto.setUserId(requestId);
		
		System.out.println("pageNum:"+pageNum);
		System.out.println("selectRowNum:"+selectRowNum);
		System.out.println("SearchCustNm:"+customerDto.getSearchCustNm());
		
		if(persetCustDto.getMaxrow() != 0) {
			customerService.UpdatePersetCust(persetCustDto);
		}
		PersetCustDto persetCust = customerService.GetPersetCust(requestId);
		
		int maxPaging = 10;//페이징 최대 갯수
		int maxRow = persetCust.getMaxrow(); //페이지당 최대 로우 갯수
		
		//업체명 검색 input이 null로 왔을 경우 where절에 like를 할 수 없으므로 빈값을 넣어줌
		String searchCustNm = customerDto.getSearchCustNm();
		if(searchCustNm == null) {
			searchCustNm = "";
		}
		
		PageInfo<CustomerDto> custList = new PageInfo<>(customerService.GetCustList(pageNum, maxRow, searchCustNm), maxPaging);
		
		//선택한 업체 정보를 분리하여 디테일 영역에 뿌려줄 데이터
		CustomerDto selectCustInfo = new CustomerDto();
		if(custList.getSize() != 0) {	
			if(selectRowNum != 0){
				int i = 0;
				selectCustInfo = custList.getList().get(i);
				
				for(i = 0; selectRowNum < custList.getList().get(i).getRowNum(); i++) {
					selectCustInfo = custList.getList().get(i+1);
				};
			}else{
				selectCustInfo = custList.getList().get(0);
			}
		}
		
		mv.addObject("PersetCust", persetCust);
		mv.addObject("CustList", custList);
		mv.addObject("SearchCustNm", searchCustNm);
		System.out.println("searchCustNm2:"+searchCustNm);
		mv.addObject("SelectCust", selectCustInfo);
		
		return mv;
	}
	
	//업체 관리 수정
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.PUT)
	void CustomerUpdate(CustomerDto customerDto, HttpServletRequest servletRequest) throws Exception {
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		customerDto.setUpdateUser(requestId);
		customerService.CustomerUpdate(customerDto);
	}
	
	//업체 관리 삭제
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.DELETE)
	void CustomerDelete(CustomerDto customerDto, HttpServletRequest servletRequest) throws Exception{
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		customerDto.setDeleteUser(requestId);
		customerService.CustomerDelete(customerDto);
	}
	
	//업체 관리 등록
	@ResponseBody
	@RequestMapping(value="/dworld/customer/control", method = RequestMethod.POST)
	void CustomerInsert(CustomerDto customerDto, HttpServletRequest servletRequest) throws Exception{
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		customerDto.setInsertUser(requestId);
		customerService.CustomerInsert(customerDto);
	}
}

