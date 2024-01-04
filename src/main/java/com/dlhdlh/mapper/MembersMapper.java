package com.dlhdlh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MembersDto;

@Mapper
public interface MembersMapper {

	public int LoginCheck(MembersDto membersDto) throws Exception;

	public MembersDto SelectMemberInfo(String userId) throws Exception;

	public void InsertMember(MembersDto membersDto) throws Exception;

	public int UserIdCheck(MembersDto membersDto) throws Exception;
	
}
