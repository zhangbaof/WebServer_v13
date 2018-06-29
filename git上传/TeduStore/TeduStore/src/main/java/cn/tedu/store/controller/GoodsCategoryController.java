package cn.tedu.store.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.store.bean.GoodsCategory;
import cn.tedu.store.service.GoodsCategoryService;

@Controller
@RequestMapping("/goods_category")
public class GoodsCategoryController {

	@Resource
	private GoodsCategoryService goodsCategoryService;
	
	
	
	@RequestMapping("/list.do")
	public String showGoodsCategoryList(Integer page,ModelMap map,HttpServletRequest request) {
		if(page==null||page<0) {
			page=1;
		}
	request.setAttribute("aaaa", 555);
		
		
		List<GoodsCategory> data = goodsCategoryService.getGoodsCategoryList(page);
		map.addAttribute("data",data);
		return "goods_category_list";
	}
}
