package com.dlhdlh.dto;

import lombok.Data;

@Data
public class RefrigeratorDataListDto {
	int idx;
	String itemName;
	String insertDt;
	String purchasedDt;
	int amt;
	String finishYn;
	String purchasedYn;
	String type;
	String afterDay;
	String userId;
}
