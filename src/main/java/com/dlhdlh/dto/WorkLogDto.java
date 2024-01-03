package com.dlhdlh.dto;

import lombok.Data;

@Data
public class WorkLogDto {
	int idx;
	String title;
	String content;
	String insertDt;
	String insertUser;
	String updateDt;
	String updateUser;
	String deleteDt;
	String deleteUser;
	String deletedYn;
	String receiptDt;
	String dueDt;
	String complDt;
	int custCd;
	String custNm;
	String userId;
	String complYn;
	String order1;
	String order2;
	String order3;
	int rowNum;
	
}
