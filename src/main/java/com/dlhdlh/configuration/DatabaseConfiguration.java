package com.dlhdlh.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//설정 파일을 자바 클래스 기반의 소스코드로 설정하도록 해주는 어노테이션
@Configuration
//설정 파일을 지정할 때 사용하는 어노테이션
//여러개 사용 시 여러 개의 설정 파일을 지정할 수 있음
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	///// hikari CP 를 이용한 데이터 베이스 연결 설정 /////
  @Bean
	//	설정 파일에서 사용할 속성을 지정하기 위한 어노테이션
	//	prefix 로 지정한 내용을 가지고 있는 속성을 가져와서 사용
  @ConfigurationProperties(prefix="spring.datasource.hikari") //spring.datasource.hikari 안의 속성을 모두 불러 온다.
  public HikariConfig hikariConfig() {
     return new HikariConfig();
  }
 

  @Bean
//	위에서 설정한 내용을 가지고 실제 데이터 베이스와 연결
  public DataSource dataSource() throws Exception {
     DataSource dataSource = new HikariDataSource(hikariConfig());
     System.out.println(dataSource.toString());
     return dataSource;
  }
  
	///// mybatis 설정 /////
//	mybatis란?
//	객체 지향 언어인 자바의 관계형 데이터 베이스 프로그래밍을 좀 더 쉽게 할 수 있도록 도와주는 데이터 베이스 프레임워크(ORM)
//	장점 : 복잡한 쿼리, 다이나믹한 쿼리에 강함, 프로그램 소스 코드와 SQL 쿼리를 분리하여 소스코드의 간결성 및 유지보수성을 향상
	
//	ORM : Object Relational Mapping의 줄임말로 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)하는 프레임워크
//	- 객체지향 프로그래밍은 클래스를 사용하고, 관계형 데이터 베이스는 테이블을 사용하기 때문에 객체 모델과 관계형 데이터베이스 모델간의 불일치가 존재함
//	- ORM을 통해서 객체와 모델간의 불일치를 해결하는데 사용함
//	- 객체를 통해서 간접적으로 데이터베이스를 컨트롤할 수 있음
//	- 프로그램언어와 데이터 베이스의 종류와 상관없이 사용하는 방식을 모두 통일해서 사용할 수 있음
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		mybatis 사용 선언
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		mybatis에서 사용하는 데이터베이스 설정
		sqlSessionFactoryBean.setDataSource(dataSource);
//		mybatis와 자바클래스를 연동할 xml 파일 위치 설정
//		classpath - /src/main/resources 폴더를 의미
//		/mapper/**/ - resources 폴더 아래의 mapper 폴더 밑에 있는 모든 폴더(**)
//		/sql-*.xml - 이름이 sql- 로 시작하고 확장자가 xml 인 모든 파일
		// 현재 xml이 mapper 직 하위에 붙어 있지만 **로 처리를 하여도 적용 된다.
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/sql/**/sql-*.xml"));
//		mybatis 추가 설정
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
 
 @Bean
 public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
 {
	   return new SqlSessionTemplate(sqlSessionFactory);
 }
 
 @Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
//	지정한 설정 파일에서 mybatis 설정 속성 가져오기
//	mybatis 설정 사용
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}
 
}