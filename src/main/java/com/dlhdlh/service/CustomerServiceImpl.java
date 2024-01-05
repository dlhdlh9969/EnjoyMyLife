package com.dlhdlh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.PersetCustDto;
import com.dlhdlh.mapper.CustomerMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	public CustomerMapper customerMapper;
	
	@Override
	public Page<CustomerDto> GetCustList(int pageNum, int maxRow, String searchCustNm) throws Exception {
		PageHelper.startPage(pageNum, maxRow);
		return customerMapper.GetCustList(searchCustNm);
	}

	@Override
	public void CustomerUpdate(CustomerDto customerDto) throws Exception {
		customerMapper.CustomerUpdate(customerDto);
	}

	@Override
	public void CustomerDelete(CustomerDto customerDto) throws Exception {
		customerMapper.CustomerDelete(customerDto);
	}

	@Override
	public void CustomerInsert(CustomerDto customerDto) throws Exception {
		customerMapper.CustomerInsert(customerDto);
	}

	@Override
	public PersetCustDto GetPersetCust(String userId) throws Exception {
		return customerMapper.GetPersetCust(userId);
	}

	@Override
	public void UpdatePersetCust(PersetCustDto persetCustDto) throws Exception {
		customerMapper.UpdatePersetCust(persetCustDto);
	}

}
