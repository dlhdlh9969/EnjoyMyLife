package com.dlhdlh.service;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.github.pagehelper.Page;

public interface CustomerService {

	Page<CustomerDto> GetCustList(int pageNum, int maxRow, String searchCustNm) throws Exception;

	void CustomerUpdate(CustomerDto customerDto) throws Exception;

	void CustomerDelete(CustomerDto customerDto) throws Exception;

	void CustomerInsert(CustomerDto customerDto) throws Exception;

	PersetCustDto GetPersetCust(String userId) throws Exception;

	void UpdatePersetCust(PersetCustDto persetCustDto)throws Exception;
}
