package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.CustFavorDto;
import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.github.pagehelper.Page;

public interface CustomerService {

	Page<CustomerDto> GetCustList(int pageNum, int maxRow, String searchCustNm) throws Exception;

	void CustomerUpdate(CustomerDto customerDto) throws Exception;

	void CustomerDelete(CustomerDto customerDto) throws Exception;

	void CustomerInsert(CustomerDto customerDto) throws Exception;

	PersetCustDto GetPersetCust(String userId) throws Exception;

	void UpdatePersetCust(PersetCustDto persetCustParam)throws Exception;

	void SetNewMember(String requestId) throws Exception;

	List<String> GetCustNmList() throws Exception;

	String CustFavorToggle(CustFavorDto custFavorParam) throws Exception;
}
