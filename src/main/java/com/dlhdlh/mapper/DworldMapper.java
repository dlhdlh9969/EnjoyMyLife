package com.dlhdlh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.dto.PrevPageDto;

@Mapper
public interface DworldMapper {

	DworldValuesDto DworldValues(String varName) throws Exception;

	void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception;

	void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception;

	String SelectPrevPage(PrevPageDto prevPageDto) throws Exception;
}
