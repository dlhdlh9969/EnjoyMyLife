package com.dlhdlh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.PersetWorkLogDto;
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
	public void InsertWorkLog(WorkLogDto worklogDto) throws Exception {
		worklogMapper.InsertWorkLog(worklogDto);
	}

	@Override
	public WorkLogDto GetWorkLogDetail(WorkLogDto worklogDto) throws Exception {
		return worklogMapper.GetWorkLogDetail(worklogDto);
	}

	@Override
	public void DeleteWorkLog(WorkLogDto worklogDto) throws Exception {
		worklogMapper.DeleteWorkLog(worklogDto);
	}

	@Override
	public void UpdateWorkLog(WorkLogDto worklogDto) throws Exception {
		worklogMapper.UpdateWorkLog(worklogDto);
	}

	@Override
	public void UpdatePersetWorkLog(PersetWorkLogDto persetWorkLogDto) throws Exception {
		worklogMapper.UpdatePersetWorkLog(persetWorkLogDto);
	}

	@Override
	public PersetWorkLogDto GetPersetWorkLog(String userId) throws Exception {
		return worklogMapper.GetPersetWorkLog(userId);
	}

	@Override
	public void SetNewMember(String requestId) throws Exception {
		worklogMapper.SetNewMember(requestId);
		
	}

}
