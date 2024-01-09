package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.MembersDto;

public interface MembersService {

	//접속자 정보
	MembersDto LoginMemberInfo(MembersDto membersDto) throws Exception;

	//회원가입 프로세스
	void InsertMember(MembersDto membersDto) throws Exception;

	MembersDto ManagerCheck(String userId) throws Exception;

	List<MembersDto> UserList(MembersDto membersDto) throws Exception;

	void UpdateMembers(MembersDto membersDto) throws Exception;

}
