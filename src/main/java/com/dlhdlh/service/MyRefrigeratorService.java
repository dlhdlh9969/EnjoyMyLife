package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.MyRefrigeratorDataListDto;

public interface MyRefrigeratorService {

	//이름 리스트 가져오기
	List<MyRefrigeratorDataListDto> GetNameList(String requestId) throws Exception;

	//장바구니 추가
	void InsertWishList(MyRefrigeratorDataListDto myRefrigeratorDataListDto) throws Exception;

	// 이름으로 마지막 데이터 가져오기
	MyRefrigeratorDataListDto GetLastData(MyRefrigeratorDataListDto paramDto) throws Exception;

	List<MyRefrigeratorDataListDto> GetWishItemList(String requestId) throws Exception;

	void DeleteWishList(int idx) throws Exception;

	void UpdatePurChase(MyRefrigeratorDataListDto paramDto) throws Exception;

	List<MyRefrigeratorDataListDto> RefrigeratorList(MyRefrigeratorDataListDto paramDto) throws Exception;
	
	void UpdateRefrigeratorList(int idx) throws Exception;

	void DeleteRefrigeratorList(int idx) throws Exception;

	List<MyRefrigeratorDataListDto> GetTypeList(String requestId) throws Exception;

	List<MyRefrigeratorDataListDto> PurchaseList(MyRefrigeratorDataListDto paramDto) throws Exception;

	void DeletePurchaseList(int idx) throws Exception;

	void UpdatePurChaseList(MyRefrigeratorDataListDto paramDto) throws Exception;

}
