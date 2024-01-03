package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.WorkLogDto;
import com.github.pagehelper.Page;

public interface WorkLogService {

	Page<WorkLogDto> GetWorkLogList(int pageNum, int maxRow, WorkLogDto worklogDto) throws Exception;

	List<CustomerDto> GetCustList(String searchCustNm) throws Exception;

	void InsertWorkLog(WorkLogDto worklogDto) throws Exception;

}
