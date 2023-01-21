package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.MyRefrigeratorDataListDto;
import com.dlhdlh.mapper.MyRefrigeratorMapper;

@Service
public class MyRefrigeratorServiceImpl implements MyRefrigeratorService {
	
	@Autowired
	MyRefrigeratorMapper myRefrigeratorMapper;

	//이름 리스트 가져오기
	@Override
	public List<MyRefrigeratorDataListDto> GetNameList(String requestId) throws Exception {
		return myRefrigeratorMapper.GetNameList(requestId);
	}

	//  장바구니 추가
	@Override
	public void InsertWishList(MyRefrigeratorDataListDto myRefrigeratorDataListDto) throws Exception {
		myRefrigeratorMapper.InsertWishList(myRefrigeratorDataListDto);
	}

	//이름으로 마지막 데이터 가져오기
	@Override
	public MyRefrigeratorDataListDto GetLastData(MyRefrigeratorDataListDto paramDto) throws Exception {
		return myRefrigeratorMapper.GetLastData(paramDto);
	}

	// 장바구니 리스트 가져오기
	@Override
	public List<MyRefrigeratorDataListDto> GetWishItemList(String requestId) throws Exception {
		return myRefrigeratorMapper.GetWishItemList(requestId);
	}

	// 장바구지 리스트에서 삭제
	@Override
	public void DeleteWishList(int idx) throws Exception {
		myRefrigeratorMapper.DeleteWishList(idx);
	}

	// 장바구니 리스트중 구매정보 업데이트
	@Override
	public void UpdatePurChase(MyRefrigeratorDataListDto paramDto) throws Exception {
		myRefrigeratorMapper.UpdatePurChase(paramDto);
	}

	// 냉장고 리스트

	@Override
	public List<MyRefrigeratorDataListDto> RefrigeratorList(MyRefrigeratorDataListDto paramDto) throws Exception {
		return myRefrigeratorMapper.RefrigeratorList(paramDto);
	}


	@Override
	public void UpdateRefrigeratorList(int idx) throws Exception {
		myRefrigeratorMapper.UpdateRefrigeratorList(idx);
	}

	@Override
	public void DeleteRefrigeratorList(int idx) throws Exception {
		myRefrigeratorMapper.DeleteRefrigeratorList(idx);
	}

	@Override
	public List<MyRefrigeratorDataListDto> GetTypeList(String requestId) throws Exception {
		return myRefrigeratorMapper.GetTypeList(requestId);
	}

	@Override
	public List<MyRefrigeratorDataListDto> PurchaseList(MyRefrigeratorDataListDto paramDto) throws Exception {
		return myRefrigeratorMapper.PurchaseList(paramDto);
	}

	@Override
	public void DeletePurchaseList(int idx) throws Exception {
		myRefrigeratorMapper.DeletePurchaseList(idx);
	}

	@Override
	public void UpdatePurChaseList(MyRefrigeratorDataListDto paramDto) throws Exception {
		myRefrigeratorMapper.UpdatePurChaseList(paramDto);
	}

}
