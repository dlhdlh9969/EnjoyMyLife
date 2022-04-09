package com.dlhdlh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dlhdlh.dto.MyRefrigeratorDataListDto;

@Mapper
public interface MyRefrigeratorMapper {

	//이름 리스트 가져오기
	public List<MyRefrigeratorDataListDto> GetNameList() throws Exception;

	public void InsertWishList(MyRefrigeratorDataListDto myRefrigeratorDataListDto) throws Exception;

	public MyRefrigeratorDataListDto GetLastData(String itemName) throws Exception;

	public List<MyRefrigeratorDataListDto> GetWishItemList() throws Exception;

	public void DeleteWishList(int idx) throws Exception;

	public void UpdatePurChase(MyRefrigeratorDataListDto paramDto) throws Exception;

	public List<MyRefrigeratorDataListDto> RefrigeratorList() throws Exception;

	public void UpdateRefrigeratorList(int idx) throws Exception;

	public void DeleteRefrigeratorList(int idx) throws Exception;

	public List<MyRefrigeratorDataListDto> GetTypeList() throws Exception;

	public List<MyRefrigeratorDataListDto> RefrigeratorTypeList(String type) throws Exception;

	public List<MyRefrigeratorDataListDto> PurchaseList() throws Exception;

	public List<MyRefrigeratorDataListDto> PurchaseItemNameList(String itemName) throws Exception;

	public void DeletePurchaseList(int idx) throws Exception;

	public void UpdatePurChaseList(MyRefrigeratorDataListDto paramDto) throws Exception;

}
