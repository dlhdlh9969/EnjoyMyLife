package com.dlhdlh.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dlhdlh.dto.PrevPageDto;
import com.dlhdlh.service.DworldService;

@Controller
public class DworldController {
	
	@Autowired
	DworldService dworldService;
	
	@RequestMapping("/")
	public String IndexPage() throws Exception{
		return "index";
	}
	
	@RequestMapping("/dworld/error")
	public String ErrorPage() throws Exception{
		return "error";
	}
	
	//현재 페이지를 저장하여 이전 페이지로 돌아가기 위한 경로에 사용
	public String PrevPage(HttpServletRequest servletRequest) {
		StringBuilder stringBuilder = new StringBuilder(servletRequest.getRequestURI().toString());
	    String requestURL = stringBuilder.toString();  //현재 URL 문자로 변환
	    String result = requestURL+"?";
	    Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = servletRequest.getParameter(paramName);
            result = result+paramName+"="+paramValue+"&";
        }
		return result;
	}
	
	// 현재 페이지 주소 저장
	public void SetPrevPage(HttpServletRequest servletRequest, String menu) throws Exception {
		String url = PrevPage(servletRequest);
		String userId = servletRequest.getSession().getAttribute("userId").toString();
		PrevPageDto prevPageDto = new PrevPageDto();
		prevPageDto.setUserId(userId);
		prevPageDto.setUrl(url);
		prevPageDto.setMenu(menu);
		
		try {
			dworldService.InsertPrevUrl(prevPageDto);
		} catch (Exception e) {
			dworldService.UpdatePrevUrl(prevPageDto);
		}
	}
	
	// 이전 페이지 주소 조회
	public String GetPrevPage(HttpServletRequest servletRequest, String menu) throws Exception {
		String userId = servletRequest.getSession().getAttribute("userId").toString();
		PrevPageDto prevPageDto = new PrevPageDto();
		prevPageDto.setUserId(userId);
		prevPageDto.setMenu(menu);
		String prevPage = dworldService.SelectPrevPage(prevPageDto);
		return prevPage;
	}
}