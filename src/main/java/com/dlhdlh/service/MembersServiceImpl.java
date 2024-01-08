package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.MembersDto;
import com.dlhdlh.mapper.MembersMapper;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	MembersMapper membersMapper;
	
	//로그인 체크
	@Override
	public int LoginCheck(MembersDto membersDto) throws Exception {
		return membersMapper.LoginCheck(membersDto);
	}

	//접속자 정보
	@Override
	public MembersDto SelectMemberDto(String userId) throws Exception {
		return membersMapper.SelectMemberInfo(userId);
	}
	
	//회원가입
	@Override
	public void InsertMember(MembersDto membersDto) throws Exception {
		membersMapper.InsertMember(membersDto);
		membersMapper.InsertPersetCust(membersDto);
		membersMapper.InsertPersetWorkLog(membersDto);
	}

	//회원가입 ID 체크
	@Override
	public int UserIdCheck(MembersDto membersDto) throws Exception {
		return membersMapper.UserIdCheck(membersDto);
	}

	@Override
	public MembersDto ManagerCheck(String userId) throws Exception {
		return membersMapper.ManagerCheck(userId);
	}

	@Override
	public List<MembersDto> UserList(MembersDto membersDto) throws Exception {
		return membersMapper.UserList(membersDto);
	}
	
}
