package com.dlhdlh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.MyRefrigeratorDataListDto;
import com.dlhdlh.service.MyRefrigeratorService;

@Controller
public class MyRefrigeratorController {

	@Autowired
	MyRefrigeratorService myRefrigeratorService;
	
	// 장바구니 리스트
	@RequestMapping("re/myWishList")
	public ModelAndView MyWishList() throws Exception {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyWishList");
		
		List<MyRefrigeratorDataListDto> nameList = myRefrigeratorService.GetNameList();
		List<MyRefrigeratorDataListDto> itemList = myRefrigeratorService.GetWishItemList();
		List<MyRefrigeratorDataListDto> typeList = myRefrigeratorService.GetTypeList();
		mv.addObject("pageName", "MyWishList");
		mv.addObject("NameList", nameList);
		mv.addObject("TypeList", typeList);
		mv.addObject("ItemList", itemList);
		return mv;
	}
	
	// 장바구니 추가
	@RequestMapping("re/insertWishList")
	public String InsertWishList(@RequestParam(required = false, defaultValue = "") String itemName) throws Exception {
		MyRefrigeratorDataListDto paramDto = new MyRefrigeratorDataListDto();
		MyRefrigeratorDataListDto lastDataDto = new MyRefrigeratorDataListDto();
		if (!itemName.equals("")) {
			try {
				lastDataDto = myRefrigeratorService.GetLastData(itemName);
				paramDto.setAmt(lastDataDto.getAmt());
				paramDto.setType(lastDataDto.getType());
			} catch(Exception e) {
				paramDto.setAmt(0);
				paramDto.setType("");
			}
			paramDto.setItemName(itemName);
			myRefrigeratorService.InsertWishList(paramDto);
		}
		return "redirect:/re/myWishList";
	}
	
	//장바구니 삭제
	@RequestMapping("re/deleteWishList")
	public String DeleteWishList(@RequestParam int idx) throws Exception{
		myRefrigeratorService.DeleteWishList(idx);
		return "redirect:/re/myWishList";
	}
	
	//구매 업데이트
	@RequestMapping("re/updatePurchase")
	public String UpdatePurchase(MyRefrigeratorDataListDto paramDto, @RequestParam String amtFormat)  throws Exception{
		
		amtFormat = amtFormat.replace(",", "");
		int amt = Integer.parseInt(amtFormat);
		paramDto.setAmt(amt);
		myRefrigeratorService.UpdatePurChase(paramDto);
		return "redirect:/re/myWishList";
	}
	
	// 냉장고리스트
	@RequestMapping("re/refrigerator")
	public ModelAndView MyRefrigeratorList(@RequestParam(required = false, defaultValue = "") String type) throws Exception  {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyRefrigeratorList");
		
		List<MyRefrigeratorDataListDto> refrigeratorList;
		
		if(type.equals("")) {
			refrigeratorList = myRefrigeratorService.RefrigeratorList();
		}else {
			refrigeratorList = myRefrigeratorService.RefrigeratorTypeList(type);
		}
		
		List<MyRefrigeratorDataListDto> typeList = myRefrigeratorService.GetTypeList();
		mv.addObject("TypeList", typeList);
		mv.addObject("RefrigeratorList", refrigeratorList);
		mv.addObject("pageName", "MyRefrigeratorList");
		return mv;
	}
	
	// 구매취소
	@RequestMapping("re/updateRefrigeratorList")
	public String UpdateRefrigeratorList(@RequestParam int idx) throws Exception{
		myRefrigeratorService.UpdateRefrigeratorList(idx);
		return "redirect:/re/refrigerator";
	}
	
	// 재료 소진
	@RequestMapping("re/deleteRefrigeratorList")
	public String DeleteRefrigeratorList(@RequestParam int idx) throws Exception{
		myRefrigeratorService.DeleteRefrigeratorList(idx);
		return "redirect:/re/refrigerator";
	}
	
	// 구매내역 리스트
	@RequestMapping("re/purchaselist")
	public ModelAndView MyPurchaseList(@RequestParam(required = false, defaultValue = "") String itemName) throws Exception  {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyPurchaselistList");
		
		List<MyRefrigeratorDataListDto> purchaseList;
		
		if(itemName.equals("")) {
			purchaseList = myRefrigeratorService.PurchaseList();
		}else {
			purchaseList = myRefrigeratorService.PurchaseItemNameList(itemName);
		}
		List<MyRefrigeratorDataListDto> nameList = myRefrigeratorService.GetNameList();
		
		List<MyRefrigeratorDataListDto> typeList = myRefrigeratorService.GetTypeList();
		mv.addObject("TypeList", typeList);
		mv.addObject("NameList", nameList);
		mv.addObject("PurchaseList", purchaseList);
		mv.addObject("pageName", "MyPurchaselistList");
		return mv;
	}
	
	// 구매내역 삭제
	@RequestMapping("re/deletePurchaseList")
	public String DeletePurchaseList(@RequestParam int idx) throws Exception{
		myRefrigeratorService.DeletePurchaseList(idx);
		return "redirect:/re/purchaselist";
	}
	
	//구매 업데이트
	@RequestMapping("re/updatePurchaseList")
	public String UpdatePurchaseList(MyRefrigeratorDataListDto paramDto, @RequestParam String amtFormat)  throws Exception{
			
		amtFormat = amtFormat.replace(",", "");
		int amt = Integer.parseInt(amtFormat);
		paramDto.setAmt(amt);
		myRefrigeratorService.UpdatePurChaseList(paramDto);
		return "redirect:/re/purchaselist";
	}
}
