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
		if(membersDto.getUserPw() == "") {
			membersMapper.UpdateMembers(membersDto); //Pw 제외 업데이트
		}else {
			membersMapper.UpdateMembersWithPw(membersDto); //Pw 포함 업데이트			
		}
	}
	
}
