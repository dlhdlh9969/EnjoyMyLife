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
	public List<MembersDto> UserList(MembersDto membersDto) throws Exception {
		return membersMapper.UserList(membersDto);
	}

	@Override
	public int UpdateMembers(MembersDto membersDto) throws Exception {
		return membersMapper.UpdateMembers(membersDto);
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
		PersetMemberDto getPersetMember = membersMapper.GetPersetMember(requestId);
		
		if(getPersetMember == null) {
			membersMapper.SetPersetMember(requestId);
			return membersMapper.GetPersetMember(requestId);
		}else {
			return getPersetMember;
		}
	}

	@Override
	public String GetUserAuth(String requestId) throws Exception {
		// TODO Auto-generated method stub
		return membersMapper.GetUserAuth(requestId);
	}

	@Override
	public int DeleteMember(String userId) throws Exception {
		// TODO Auto-generated method stub
		return membersMapper.DeleteMember(userId);
	}
}
