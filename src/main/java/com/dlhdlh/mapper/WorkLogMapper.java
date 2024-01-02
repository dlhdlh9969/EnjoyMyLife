package com.dlhdlh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.WorkLogDto;
import com.github.pagehelper.Page;

@Mapper
public interface WorkLogMapper {

	Page<WorkLogDto> GetWorkLogList(WorkLogDto worklogDto) throws Exception;

}
