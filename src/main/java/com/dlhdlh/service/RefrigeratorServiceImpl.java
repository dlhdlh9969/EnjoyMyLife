package com.dlhdlh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlhdlh.dto.RefrigeratorDataListDto;
import com.dlhdlh.mapper.RefrigeratorMapper;

@Service
public class RefrigeratorServiceImpl implements RefrigeratorService {
	
	@Autowired
	RefrigeratorMapper refrigeratorMapper;

	//이름 리스트 가져오기
	@Override
	public List<RefrigeratorDataListDto> GetNameList(String requestId) throws Exception {
		return refrigeratorMapper.GetNameList(requestId);
	}

	//  장바구니 추가
	@Override
	public void InsertWishList(RefrigeratorDataListDto refrigeratorDataListDto) throws Exception {
		refrigeratorMapper.InsertWishList(refrigeratorDataListDto);
	}

	//이름으로 마지막 데이터 가져오기
	@Override
	public RefrigeratorDataListDto GetLastData(RefrigeratorDataListDto paramDto) throws Exception {
		return refrigeratorMapper.GetLastData(paramDto);
	}

	// 장바구니 리스트 가져오기
	@Override
	public List<RefrigeratorDataListDto> GetWishItemList(String requestId) throws Exception {
		return refrigeratorMapper.GetWishItemList(requestId);
	}

	// 장바구지 리스트에서 삭제
	@Override
	public void DeleteWishList(int idx) throws Exception {
		refrigeratorMapper.DeleteWishList(idx);
	}

	// 장바구니 리스트중 구매정보 업데이트
	@Override
	public void UpdatePurChase(RefrigeratorDataListDto paramDto) throws Exception {
		refrigeratorMapper.UpdatePurChase(paramDto);
	}

	// 냉장고 리스트

	@Override
	public List<RefrigeratorDataListDto> RefrigeratorList(RefrigeratorDataListDto paramDto) throws Exception {
		return refrigeratorMapper.RefrigeratorList(paramDto);
	}


	@Override
	public void UpdateRefrigeratorList(int idx) throws Exception {
		refrigeratorMapper.UpdateRefrigeratorList(idx);
	}

	@Override
	public void DeleteRefrigeratorList(int idx) throws Exception {
		refrigeratorMapper.DeleteRefrigeratorList(idx);
	}

	@Override
	public List<RefrigeratorDataListDto> GetTypeList(String requestId) throws Exception {
		return refrigeratorMapper.GetTypeList(requestId);
	}

	@Override
	public List<RefrigeratorDataListDto> PurchaseList(RefrigeratorDataListDto paramDto) throws Exception {
		return refrigeratorMapper.PurchaseList(paramDto);
	}

	@Override
	public void DeletePurchaseList(int idx) throws Exception {
		refrigeratorMapper.DeletePurchaseList(idx);
	}

	@Override
	public void UpdatePurChaseList(RefrigeratorDataListDto paramDto) throws Exception {
		refrigeratorMapper.UpdatePurChaseList(paramDto);
	}

}
