package com.dlhdlh.service;

import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.DworldValuesDto;
import com.dlhdlh.mapper.DworldMapper;

@Service
public class DworldServiceImpl implements DworldService {
	
	@Autowired
	DworldMapper dworldMapper;
	
	@Override
	public DworldValuesDto DworldValues(String varName) throws Exception {
		return dworldMapper.DworldValues(varName);
	}
	// prevPage 저장 방식을 session으로 변경함 2024.01.21 김동환
//	@Override
//	public void UpdatePrevUrl(PrevPageDto prevPageDto) throws Exception {
//		dworldMapper.UpdatePrevUrl(prevPageDto);
//	}
//	@Override
//	public void InsertPrevUrl(PrevPageDto prevPageDto) throws Exception {
//		dworldMapper.InsertPrevUrl(prevPageDto);
//	}
//	@Override
//	public String SelectPrevPage(PrevPageDto prevPageDto) throws Exception {
//		return dworldMapper.SelectPrevPage(prevPageDto);
//	}

	@Override
	public List<DworldValuesDto> ListDworldValues(String varName) throws Exception {
		return dworldMapper.ListDworldValues(varName);
	}
	
	// 현재 페이지 주소 저장
	public void SetPrevPage(HttpServletRequest servletRequest) throws Exception {
		String getPrevPage = GetPrevPage(servletRequest);
		servletRequest.getSession().setAttribute("prevPage", getPrevPage);
	}
	
	//현재 페이지를 저장하여 이전 페이지로 돌아가기 위한 경로에 사용
	public String GetPrevPage(HttpServletRequest servletRequest) {
		StringBuilder getStringBuilder = new StringBuilder(servletRequest.getRequestURI().toString());
	    String getRequestURL = getStringBuilder.toString();  //현재 URL 문자로 변환
	    String result = getRequestURL+"?";
	    Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = servletRequest.getParameter(paramName);
            result += paramName+"="+paramValue+"&";
        }
		return result;
	}
	
	// SHA-256 암호화를 위한 메서드
	public String PasswordSHA256(String planText) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(planText.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
