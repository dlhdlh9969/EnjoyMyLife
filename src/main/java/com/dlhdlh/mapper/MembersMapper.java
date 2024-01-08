package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MembersDto;

@Mapper
public interface MembersMapper {

	public int LoginCheck(MembersDto membersDto) throws Exception;

	public MembersDto SelectMemberInfo(String userId) throws Exception;

	public void InsertMember(MembersDto membersDto) throws Exception;

	public int UserIdCheck(MembersDto membersDto) throws Exception;

	public void InsertPersetCust(MembersDto membersDto) throws Exception;

	public void InsertPersetWorkLog(MembersDto membersDto) throws Exception;

	public MembersDto ManagerCheck(String userId) throws Exception;

	public List<MembersDto> UserList(MembersDto membersDto) throws Exception;
	
}
