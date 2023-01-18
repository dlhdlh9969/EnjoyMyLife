package com.dlhdlh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MembersDto;

@Mapper
public interface MembersMapper {

	public int loginCheck(MembersDto loginRequest) throws Exception;

	public MembersDto selectMemberInfo(String userId) throws Exception;

	public void insertMember(MembersDto memberInfo) throws Exception;

	public int userIdCheck(MembersDto memberInfo) throws Exception;
	
}
