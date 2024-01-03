package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.CustomerDto;
import com.dlhdlh.dto.WorkLogDto;
import com.github.pagehelper.Page;

@Mapper
public interface WorkLogMapper {

	Page<WorkLogDto> GetWorkLogList(WorkLogDto worklogDto) throws Exception;

	List<CustomerDto> GetCustList(String searchCustNm) throws Exception;

	void InsertWorkLog(WorkLogDto worklogDto) throws Exception;
}
