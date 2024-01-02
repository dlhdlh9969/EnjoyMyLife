package com.dlhdlh.service;

import com.dlhdlh.dto.WorkLogDto;
import com.github.pagehelper.Page;

public interface WorkLogService {

	Page<WorkLogDto> GetWorkLogList(int pageNum, int maxRow, WorkLogDto worklogDto) throws Exception;

}
