package com.dlhdlh.dto;

import lombok.Data;

@Data
public class PersetWorkLogDto {
	String userId;
	int maxrow;
	String custNm;
	String title;
	String content;
	String documentType;
	String startDt;
	String endDt;
	String complYn;
	String order1;
	String order2;
	String orderby1;
}
