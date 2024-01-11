package com.dlhdlh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.dto.PrevPageDto;
import com.dlhdlh.mapper.DworldMapper;

@Service
public class DworldServiceImpl implements DworldService {
	
	@Autowired
	DworldMapper dworldMapper;
	
	@Override
	public DworldValuesDto DworldValues(String varName) throws Exception {
		return dworldMapper.DworldValues(varName);
	}

	@Override
	public void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception {
		dworldMapper.UpdatePrevUrl(prevPageDto);
	}

	@Override
	public void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception {
		dworldMapper.InsertPrevUrl(prevPageDto);
	}

	@Override
	public String SelectPrevPage(PrevPageDto prevPageDto) throws Exception {
		return dworldMapper.SelectPrevPage(prevPageDto);
	}

}