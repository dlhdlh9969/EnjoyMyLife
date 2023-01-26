package com.dlhdlh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dlhdlh.service.MembersService;

@Controller
public class CarManage {
	
	@Autowired
	private MembersService membersService;
	
	@RequestMapping("/carmanage/mainpage")
	public String CarManagePage(){
		return "CarManage/MainPage";
	}
}
