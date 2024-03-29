package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.dto.PersetMemberDto;

@Mapper
public interface MembersMapper {

	public MembersDto GetLoginMemberInfo(MembersDto membersDto) throws Exception;

	public void InsertMember(MembersDto membersDto) throws Exception;

	public List<MembersDto> UserList(MembersDto membersDto) throws Exception;

	public int UpdateMembers(MembersDto membersDto) throws Exception;

	public void InsertPersetMember(PersetMemberDto persetMemberParam) throws Exception;

	public void UpdatePersetMember(PersetMemberDto persetMemberParam) throws Exception;

	public PersetMemberDto GetPersetMember(String requestId) throws Exception;

	public void SetPersetMember(String requestId) throws Exception;

	public String GetUserAuth(String requestId) throws Exception;

	public int DeleteMember(String userId) throws Exception;

}
