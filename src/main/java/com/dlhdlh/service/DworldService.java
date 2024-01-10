package com.dlhdlh.service;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.dto.PrevPageDto;

public interface DworldService {

	DworldValuesDto DworldValues(String varName) throws Exception;

	void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception;

	void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception;

	String SelectPrevPage(PrevPageDto prevPageDto) throws Exception;


}
