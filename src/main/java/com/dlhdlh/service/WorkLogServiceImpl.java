package com.dlhdlh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.WorkLogDto;
import com.dlhdlh.mapper.WorkLogMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class WorkLogServiceImpl implements WorkLogService {

	@Autowired
	WorkLogMapper worklogMapper;
	
	@Override
	public Page<WorkLogDto> GetWorkLogList(int pageNum, int maxRow, WorkLogDto worklogDto) throws Exception {
		PageHelper.startPage(pageNum, maxRow);
		return worklogMapper.GetWorkLogList(worklogDto);
	}

}
