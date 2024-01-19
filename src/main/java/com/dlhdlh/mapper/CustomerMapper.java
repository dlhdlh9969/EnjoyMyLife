package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.CustFavorDto;
import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.github.pagehelper.Page;

@Mapper
public interface CustomerMapper {

	Page<CustomerDto> GetCustList(String searchCustNm) throws Exception;

	void CustomerUpdate(CustomerDto customerDto) throws Exception;

	void CustomerDelete(CustomerDto customerDto) throws Exception;

	void CustomerInsert(CustomerDto customerDto) throws Exception;

	PersetCustDto GetPersetCust(String userId) throws Exception;

	void UpdatePersetCust(PersetCustDto persetCustParam) throws Exception;
	
	public void SetNewMember(String requestId) throws Exception;

	List<String> GetCustNmList() throws Exception;

	void InsertCustFavor(CustFavorDto custFavorParam) throws Exception;

	void DeleteCustFavor(CustFavorDto custFavorParam) throws Exception;
}
