package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.CustomerDto;
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

	@Override
	public List<CustomerDto> GetCustList(String searchCustNm) throws Exception {
		return worklogMapper.GetCustList(searchCustNm);
	}

	@Override
	public void InsertWorkLog(WorkLogDto worklogDto) throws Exception {
		worklogMapper.InsertWorkLog(worklogDto);
	}

	@Override
	public WorkLogDto GetWorkLogDetail(WorkLogDto worklogDto) throws Exception {
		return worklogMapper.GetWorkLogDetail(worklogDto);
	}

	@Override
	public void DeleteWorkLog(int idx) throws Exception {
		worklogMapper.DeleteWorkLog(idx);
	}

	@Override
	public void UpdateWorkLog(WorkLogDto worklogDto) throws Exception {
		worklogMapper.UpdateWorkLog(worklogDto);
	}

}
