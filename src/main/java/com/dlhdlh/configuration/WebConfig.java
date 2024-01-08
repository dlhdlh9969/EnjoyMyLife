package com.dlhdlh.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dlhdlh.interceptor.AuthorityInterceptor;
import com.dlhdlh.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	LoginInterceptor loginInterceptor;
	
	@Autowired
	AuthorityInterceptor authorityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
				
		//전체 경로 인터셉트
		registry.addInterceptor(loginInterceptor).addPathPatterns("/dworld/**");
		//권한 인터셉트
		registry.addInterceptor(authorityInterceptor).addPathPatterns("/dworld/auth/**"); 
	
	}	
}
