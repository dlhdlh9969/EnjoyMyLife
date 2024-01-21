package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.DworldValuesDto;

@Mapper
public interface DworldMapper {

	DworldValuesDto DworldValues(String varName) throws Exception;
	
	// prevPage 저장 방식을 session으로 변경함 2024.01.21 김동환
//	void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception;
//	void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception;
//	String SelectPrevPage(PrevPageDto prevPageDto) throws Exception;

	List<DworldValuesDto> GetDocumentType() throws Exception;
}
