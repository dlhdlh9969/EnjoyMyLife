package com.dlhdlh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.service.MembersService;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {

	@Autowired
	MembersService membersService; 
	
	@Override
	public boolean preHandle( @NonNull HttpServletRequest servletRequest, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception { 
		String requestId = (String) servletRequest.getSession().getAttribute("userId");
		
		// 관리자 여부 체크
		String checkUserAuth = membersService.GetUserAuth(requestId);
		
		if(checkUserAuth.equals("A")) {
			return true;
		}else if(checkUserAuth.equals("M")){
			return true;
		}
		
		response.sendRedirect("/error");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
