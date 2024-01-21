package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.DworldValuesDto;

public interface DworldService {

	DworldValuesDto DworldValues(String varName) throws Exception;

	List<DworldValuesDto> GetDocumentType() throws Exception;

// prevPage 저장 방식을 session으로 변경함 2024.01.21 김동환
//	void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception;
//	void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception;
//	String SelectPrevPage(PrevPageDto prevPageDto) throws Exception;


}
