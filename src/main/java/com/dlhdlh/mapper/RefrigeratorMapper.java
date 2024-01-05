package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.RefrigeratorDataListDto;

@Mapper
public interface RefrigeratorMapper {

	//이름 리스트 가져오기
	public List<RefrigeratorDataListDto> GetNameList(String requestId) throws Exception;

	public void InsertWishList(RefrigeratorDataListDto RefrigeratorDataListDto) throws Exception;

	public RefrigeratorDataListDto GetLastData(RefrigeratorDataListDto paramDto) throws Exception;

	public List<RefrigeratorDataListDto> GetWishItemList(String requestId) throws Exception;

	public void DeleteWishList(int idx) throws Exception;

	public void UpdatePurChase(RefrigeratorDataListDto paramDto) throws Exception;

	public void UpdateRefrigeratorList(int idx) throws Exception;

	public void DeleteRefrigeratorList(int idx) throws Exception;

	public List<RefrigeratorDataListDto> GetTypeList(String requestId) throws Exception;

	public List<RefrigeratorDataListDto> RefrigeratorList(RefrigeratorDataListDto paramDto) throws Exception;

	public List<RefrigeratorDataListDto> PurchaseList(RefrigeratorDataListDto paramDto) throws Exception;

	public void DeletePurchaseList(int idx) throws Exception;

	public void UpdatePurChaseList(RefrigeratorDataListDto paramDto) throws Exception;

}
