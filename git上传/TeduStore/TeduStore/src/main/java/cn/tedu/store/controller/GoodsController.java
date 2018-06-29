package cn.tedu.store.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;
import cn.tedu.store.service.GoodsCategoryService;
import cn.tedu.store.service.GoodsService;

@Controller
@RequestMapping("goods")
public class GoodsController {

	@Resource
	private GoodsService goodsService;
	
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@RequestMapping("/list.do")
	public String showGoodsList(
			Integer page,ModelMap modelMap,@RequestParam("id")Integer categoryId) {
		//计算翻页数据
		Integer pageCount = 20;
		Integer recordCount = goodsService.getRecordCountByCategoryId(categoryId);
		/*System.out.println(recordCount);*/
		String forword = "goods_list";
		
		
		int maxPage = recordCount/pageCount;
		//计算最多分多少页
		if(recordCount%pageCount>0) {
			maxPage++;
		}
		if(recordCount==0) {
			return forword;
		}
		
		//判断参数中的页码是否有效
		if(page==null||page<0) {
			page=1;
		}
		if(page>maxPage) {
			page=maxPage;
		}
		
		Integer offset = (page-1)*pageCount;
		
		@SuppressWarnings("deprecation")
		List<Goods> data = goodsService.getGoodsByCategoryId(categoryId, offset, null,pageCount);
		/*System.out.println(data);*/
		modelMap.addAttribute("data",data);
		modelMap.addAttribute("maxPage",maxPage);
		modelMap.addAttribute("currentPage",page);
		modelMap.addAttribute("categoryId",categoryId);
		return forword;
	}
	
	
	@RequestMapping("/details.do")
	public String showGoodsDetails(@RequestParam("id")Integer goodsId,ModelMap modelMap) {
		
		String forword = "goods_details";
		
		Goods goods = goodsService.getGoodsById(goodsId);
		if(goods==null) {
			modelMap.addAttribute("message","没有匹配的商品信息");
			return forword;
		}
		System.out.println(goods);
		String image = goods.getImage();
		String img = image.substring(0,image.lastIndexOf("/"));
		System.out.println(img);
		//通过Service根据以上商品的itemType读取通品类商品
		List<Goods> goodsList = goodsService.getGoodsListByItemType(goods.getItemType());
		GoodsCategory goodsCategory3 = goodsCategoryService.getGoodsCategoryById(goods.getCategoryId());
		GoodsCategory goodsCategory2 = goodsCategoryService.getGoodsCategoryById(goodsCategory3.getParentId());
		GoodsCategory goodsCategory1 = goodsCategoryService.getGoodsCategoryById(goodsCategory2.getParentId());
		
		System.out.println(goodsCategory2.getId());
		modelMap.addAttribute("image",img);
		modelMap.addAttribute("goods",goods);
		modelMap.addAttribute("goodsList",goodsList);
		modelMap.addAttribute("goodsCategory1",goodsCategory1);
		modelMap.addAttribute("goodsCategory2",goodsCategory2);
		modelMap.addAttribute("goodsCategory3",goodsCategory3);
		
		/*System.out.println(goods);
		System.out.println(goodsList);*/
		
		//执行转发
		return forword;
	}
	
	
	
}
