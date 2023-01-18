package com.dlhdlh.service;

import com.dlhdlh.dto.MembersDto;

public interface MembersService {

	//로그인 체크
	int loginCheck(MembersDto loginRequest) throws Exception;
	
	//접속자 정보
	MembersDto selectMemberDto(String userId) throws Exception;

	//회원가입 프로세스
	void insertMember(MembersDto memberInfo) throws Exception;

	int userIdCheck(MembersDto memberInfo) throws Exception;

}
