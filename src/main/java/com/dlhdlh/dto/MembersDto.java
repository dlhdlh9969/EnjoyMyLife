package com.dlhdlh.dto;

import lombok.Data;

@Data
public class MembersDto {
	String userId;
	String userPw;
	String userName;
	int authGrade;
	String authName;
}
