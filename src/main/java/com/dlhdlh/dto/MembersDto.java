package com.dlhdlh.dto;

import lombok.Data;

@Data
public class MembersDto {
	String userId;
	String userPw;
	String userName;
	String authority;
	String joinDt;
	String deletedYn;
}
