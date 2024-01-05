package com.dlhdlh.service;

import java.util.List;

import com.dlhdlh.dto.RefrigeratorDataListDto;

public interface RefrigeratorService {

	//이름 리스트 가져오기
	List<RefrigeratorDataListDto> GetNameList(String requestId) throws Exception;

	//장바구니 추가
	void InsertWishList(RefrigeratorDataListDto refrigeratorDataListDto) throws Exception;

	// 이름으로 마지막 데이터 가져오기
	RefrigeratorDataListDto GetLastData(RefrigeratorDataListDto paramDto) throws Exception;

	List<RefrigeratorDataListDto> GetWishItemList(String requestId) throws Exception;

	void DeleteWishList(int idx) throws Exception;

	void UpdatePurChase(RefrigeratorDataListDto paramDto) throws Exception;

	List<RefrigeratorDataListDto> RefrigeratorList(RefrigeratorDataListDto paramDto) throws Exception;
	
	void UpdateRefrigeratorList(int idx) throws Exception;

	void DeleteRefrigeratorList(int idx) throws Exception;

	List<RefrigeratorDataListDto> GetTypeList(String requestId) throws Exception;

	void DeletePurchaseList(int idx) throws Exception;

	void UpdatePurChaseList(RefrigeratorDataListDto paramDto) throws Exception;

	List<RefrigeratorDataListDto> PurchaseList(RefrigeratorDataListDto paramDto) throws Exception;

}
