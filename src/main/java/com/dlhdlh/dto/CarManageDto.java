package com.dlhdlh.dto;

import lombok.Data;

@Data
public class CarManageDto {
	int idx;
	int seq;
	int insertDt;
	int repairDt;
	String details;
	int amt;
	int totalDistance;
	String userId;
	String carType;
	String carName;
}
