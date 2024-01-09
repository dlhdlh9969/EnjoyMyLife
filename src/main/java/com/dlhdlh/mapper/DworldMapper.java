package com.dlhdlh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.DworldValuesDto;

@Mapper
public interface DworldMapper {

	DworldValuesDto DworldValues(String varName) throws Exception;

}
