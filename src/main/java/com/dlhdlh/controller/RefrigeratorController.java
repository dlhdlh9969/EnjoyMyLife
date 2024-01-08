package com.dlhdlh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dlhdlh.dto.RefrigeratorDataListDto;
import com.dlhdlh.service.RefrigeratorService;

@Controller
public class RefrigeratorController {

	@Autowired
	RefrigeratorService refrigeratorService;
	
	// 장바구니 리스트
	@RequestMapping("/dworld/re/myWishList")
	public ModelAndView MyWishList(HttpServletRequest servletRequest) throws Exception {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyWishList");
		String userId = (String) servletRequest.getSession().getAttribute("userId");
		
		List<RefrigeratorDataListDto> nameList = refrigeratorService.GetNameList(userId);
		List<RefrigeratorDataListDto> itemList = refrigeratorService.GetWishItemList(userId);
		List<RefrigeratorDataListDto> typeList = refrigeratorService.GetTypeList(userId);
		mv.addObject("PageName", "MyWishList");
		mv.addObject("NameList", nameList);
		mv.addObject("TypeList", typeList);
		mv.addObject("ItemList", itemList);
		return mv;
	}
	
	// 장바구니 추가
	@RequestMapping("/dworld/re/insertWishList")
	public String InsertWishList(@RequestParam(required = false, defaultValue = "") String itemName, 
			HttpServletRequest request) throws Exception {
		
		RefrigeratorDataListDto paramDto = new RefrigeratorDataListDto();
		RefrigeratorDataListDto recentData = new RefrigeratorDataListDto();
		HttpSession session = request.getSession();
		
		paramDto.setUserId((String) session.getAttribute("userId"));
		paramDto.setItemName(itemName);
		
		if (!itemName.equals("")) {
			try {
				recentData = refrigeratorService.GetLastData(paramDto);
				paramDto.setAmt(recentData.getAmt());
				paramDto.setType(recentData.getType());
			} catch(Exception e) {
				paramDto.setAmt(0);
				paramDto.setType("");
			}
			paramDto.setItemName(itemName);
			refrigeratorService.InsertWishList(paramDto);
		}
		return "redirect:/dworld/re/myWishList";
	}
	
	//장바구니 삭제
	@RequestMapping("/dworld/re/deleteWishList")
	public String DeleteWishList(@RequestParam int idx) throws Exception{
		refrigeratorService.DeleteWishList(idx);
		return "redirect:/re/myWishList";
	}
	
	//구매 업데이트
	@RequestMapping("/dworld/re/updatePurchase")
	public String UpdatePurchase(RefrigeratorDataListDto paramDto, @RequestParam String amtFormat)  throws Exception{
		
		amtFormat = amtFormat.replace(",", "");
		int amt = Integer.parseInt(amtFormat);
		paramDto.setAmt(amt);
		refrigeratorService.UpdatePurChase(paramDto);
		return "redirect:/re/myWishList";
	}
	
	// 냉장고리스트
	@RequestMapping("/dworld/re/refrigerator")
	public ModelAndView MyRefrigeratorList(@RequestParam(required = false, defaultValue = "") String type, HttpServletRequest request) throws Exception  {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyRefrigeratorList");
		HttpSession session = request.getSession();
		String requestId = (String) session.getAttribute("userId");
		RefrigeratorDataListDto paramDto = new RefrigeratorDataListDto();
		paramDto.setType(type);
		paramDto.setUserId(requestId);
		
		List<RefrigeratorDataListDto> refrigeratorList;
		
		refrigeratorList = refrigeratorService.RefrigeratorList(paramDto);
		
		List<RefrigeratorDataListDto> typeList = refrigeratorService.GetTypeList(requestId);
		mv.addObject("TypeList", typeList);
		mv.addObject("RefrigeratorList", refrigeratorList);
		mv.addObject("pageName", "MyRefrigeratorList");
		return mv;
	}
	
	// 구매취소
	@RequestMapping("/dworld/re/updateRefrigeratorList")
	public String UpdateRefrigeratorList(@RequestParam int idx) throws Exception{
		refrigeratorService.UpdateRefrigeratorList(idx);
		return "redirect:/re/refrigerator";
	}
	
	// 재료 소진
	@RequestMapping("/dworld/re/deleteRefrigeratorList")
	public String DeleteRefrigeratorList(@RequestParam int idx) throws Exception{
		refrigeratorService.DeleteRefrigeratorList(idx);
		return "redirect:/re/refrigerator";
	}
	
	// 구매내역 리스트
	@RequestMapping("/dworld/re/purchaselist")
	public ModelAndView MyPurchaseList(@RequestParam(required = false, defaultValue = "") String itemName, HttpServletRequest request) throws Exception  {
		ModelAndView mv = new ModelAndView("MyRefrigerator/MyPurchaselistList");
		HttpSession session = request.getSession();
		String requestId = (String) session.getAttribute("userId");
		RefrigeratorDataListDto paramDto = new RefrigeratorDataListDto();
		paramDto.setItemName(itemName);
		paramDto.setUserId(requestId);
		
		List<RefrigeratorDataListDto> purchaseList;
		
		purchaseList = refrigeratorService.PurchaseList(paramDto);
			
		List<RefrigeratorDataListDto> nameList = refrigeratorService.GetNameList(requestId);
		
		List<RefrigeratorDataListDto> typeList = refrigeratorService.GetTypeList(requestId);
		mv.addObject("TypeList", typeList);
		mv.addObject("NameList", nameList);
		mv.addObject("PurchaseList", purchaseList);
		mv.addObject("pageName", "MyPurchaselistList");
		return mv;
	}
	
	// 구매내역 삭제
	@RequestMapping("/dworld/re/deletePurchaseList")
	public String DeletePurchaseList(@RequestParam int idx) throws Exception{
		refrigeratorService.DeletePurchaseList(idx);
		return "redirect:/re/purchaselist";
	}
	
	//구매 업데이트
	@RequestMapping("/dworld/re/updatePurchaseList")
	public String UpdatePurchaseList(RefrigeratorDataListDto paramDto, @RequestParam String amtFormat)  throws Exception{
			
		amtFormat = amtFormat.replace(",", "");
		int amt = Integer.parseInt(amtFormat);
		paramDto.setAmt(amt);
		refrigeratorService.UpdatePurChaseList(paramDto);
		return "redirect:/re/purchaselist";
	}
}
