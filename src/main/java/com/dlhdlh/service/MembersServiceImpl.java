package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.dto.PersetMemberDto;
import com.dlhdlh.mapper.MembersMapper;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	MembersMapper membersMapper;

	//접속자 정보
	@Override
	public MembersDto LoginMemberInfo(MembersDto membersDto) throws Exception {
		return membersMapper.LoginMemberInfo(membersDto);
	}
	
	//회원가입
	@Override
	public void InsertMember(MembersDto membersDto) throws Exception {
		membersMapper.InsertMember(membersDto);
	}

	@Override
	public MembersDto ManagerCheck(String userId) throws Exception {
		return membersMapper.ManagerCheck(userId);
	}

	@Override
	public List<MembersDto> UserList(MembersDto membersDto) throws Exception {
		return membersMapper.UserList(membersDto);
	}

	@Override
	public void UpdateMembers(MembersDto membersDto) throws Exception {
		membersMapper.UpdateMembers(membersDto);
	}

	@Override
	public void InsertPersetMember(PersetMemberDto persetMemberParam) throws Exception {
		membersMapper.InsertPersetMember(persetMemberParam);
	}

	@Override
	public void UpdatePersetMember(PersetMemberDto persetMemberParam) throws Exception {
		membersMapper.UpdatePersetMember(persetMemberParam);
	}

	@Override
	public PersetMemberDto GetPersetMember(String requestId) throws Exception {
		return membersMapper.GetPersetMember(requestId);
	}
	
}
