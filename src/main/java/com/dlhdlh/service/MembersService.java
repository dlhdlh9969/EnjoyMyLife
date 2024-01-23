package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.dto.PersetMemberDto;

public interface MembersService {

	//접속자 정보
	MembersDto GetLoginMemberInfo(MembersDto membersDto) throws Exception;

	//회원가입 프로세스
	void InsertMember(MembersDto membersDto) throws Exception;

	List<MembersDto> UserList(MembersDto membersDto) throws Exception;

	int UpdateMembers(MembersDto membersDto) throws Exception;

	void InsertPersetMember(PersetMemberDto persetMemberParam) throws Exception;

	void UpdatePersetMember(PersetMemberDto persetMemberParam) throws Exception;

	PersetMemberDto GetPersetMember(String requestId) throws Exception;

	String GetUserAuth(String requestId) throws Exception;

	int DeleteMember(String userId) throws Exception;
}
