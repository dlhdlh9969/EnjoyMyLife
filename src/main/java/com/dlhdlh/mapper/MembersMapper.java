package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MembersDto;

@Mapper
public interface MembersMapper {

	public MembersDto LoginMemberInfo(MembersDto membersDto) throws Exception;

	public void InsertMember(MembersDto membersDto) throws Exception;

	public MembersDto ManagerCheck(String userId) throws Exception;

	public List<MembersDto> UserList(MembersDto membersDto) throws Exception;

	public void UpdateMembers(MembersDto membersDto) throws Exception;

	public void UpdateMembersWithPw(MembersDto membersDto) throws Exception;
	
}
