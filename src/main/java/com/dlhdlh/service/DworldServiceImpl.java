package com.dlhdlh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.mapper.DworldMapper;

@Service
public class DworldServiceImpl implements DworldService {
	
	@Autowired
	DworldMapper dworldMapper;
	
	@Override
	public DworldValuesDto DworldValues(String varName) throws Exception {
		return dworldMapper.DworldValues(varName);
	}

}
