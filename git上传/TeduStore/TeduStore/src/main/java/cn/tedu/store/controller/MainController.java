package cn.tedu.store.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;
import cn.tedu.store.service.GoodsCategoryService;
import cn.tedu.store.service.GoodsService;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController{

	@Resource
	private GoodsService goodsService;
	@Resource 
	private GoodsCategoryService goodsCategoryService;
	
	
	@RequestMapping("/index.do")
	public String showIndex(ModelMap modelMap) {
		Integer categoryId = 163;
		@SuppressWarnings("deprecation")
		List<Goods> hotNotebooks  = goodsService.getGoodsByCategoryId(categoryId, 0,null,3);
		List<GoodsCategory> category161 = goodsCategoryService.getGoodsCategoryByParent(161, 0, 3);
//		List<GoodsCategory> list2 = goodsCategoryService.getGoodsCategoryByParent(Integer.valueOf(list1.get(0).getId()), 0, 6);
//		List<GoodsCategory> list3 = goodsCategoryService.getGoodsCategoryByParent(Integer.valueOf(list1.get(1).getId()), 0, 6);
//		List<GoodsCategory> list4 = goodsCategoryService.getGoodsCategoryByParent(Integer.valueOf(list1.get(2).getId()), 0, 6);
		
		List<List<GoodsCategory>> subCategories = new ArrayList<List<GoodsCategory>>();
		
		for(GoodsCategory category:category161) {
			subCategories.add(goodsCategoryService.getGoodsCategoryByParent(category.getId(), null, null));
		}
		
		modelMap.addAttribute("category161",category161);
		modelMap.addAttribute("subCategories",subCategories);
		
		System.out.println(hotNotebooks);
		modelMap.addAttribute("hotNotebooks",hotNotebooks);
		return "index";
	}
	
	
	
}
