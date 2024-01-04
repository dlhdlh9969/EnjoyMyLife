package com.dlhdlh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String IndexPage() throws Exception{
		return "index";
	}
	
	@RequestMapping("/dworld/error")
	public String ErrorPage() throws Exception{
		return "error";
	}

}