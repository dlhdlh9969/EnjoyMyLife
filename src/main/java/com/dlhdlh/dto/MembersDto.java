package com.dlhdlh.dto;

import java.util.List;

import lombok.Data;

@Data
public class MembersDto {
	String userId;
	String userPw;
	String userName;
	String authority;
	String joinDt;
	List<String> datas;
}
