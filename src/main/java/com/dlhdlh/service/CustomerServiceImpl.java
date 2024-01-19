package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.CustFavorDto;
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
	public void UpdatePersetCust(PersetCustDto persetCustParam) throws Exception {
		customerMapper.UpdatePersetCust(persetCustParam);
	}

	@Override
	public void SetNewMember(String requestId) throws Exception {
		customerMapper.SetNewMember(requestId);
	}

	@Override
	public List<String> GetCustNmList() throws Exception {
		return customerMapper.GetCustNmList();
	}

	@Override
	public String CustFavorToggle(CustFavorDto custFavorParam) throws Exception {
		try {
			customerMapper.InsertCustFavor(custFavorParam);
			return "insert";
		} catch (Exception e) {
			customerMapper.DeleteCustFavor(custFavorParam);
			return "delete";
		}
	}

}
