package com.dlhdlh.service;

import com.dlhdlh.dto.PersetWorkLogDto;
import com.dlhdlh.dto.WorkLogDto;
import com.github.pagehelper.Page;

public interface WorkLogService {

	Page<WorkLogDto> GetWorkLogList(int pageNum, int maxRow, WorkLogDto worklogDto) throws Exception;

	int InsertWorkLog(WorkLogDto worklogDto) throws Exception;

	WorkLogDto GetWorkLogDetail(WorkLogDto worklogDto) throws Exception;

	void DeleteWorkLog(WorkLogDto worklogDto) throws Exception;

	void UpdateWorkLog(WorkLogDto worklogDto) throws Exception;

	void UpdatePersetWorkLog(PersetWorkLogDto persetWorkLogDto) throws Exception;

	PersetWorkLogDto GetPersetWorkLog(String userId) throws Exception;

	void SetNewMember(String requestId) throws Exception;

}
