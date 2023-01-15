package com.dlhdlh.service;

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
	public int loginCheck(MembersDto loginRequest) throws Exception {
		return membersMapper.loginCheck(loginRequest);
	}

	//접속자 정보
	@Override
	public MembersDto selectMemberDto(String userId) throws Exception {
		return membersMapper.selectMemberInfo(userId);
	}
	
}
