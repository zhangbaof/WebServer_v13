package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;

public interface GoodsCategoryService {

	List<GoodsCategory> getGoodsCategoryList(Integer page);
	List<GoodsCategory> getGoodsCategoryByParent(Integer parentId,Integer offset,Integer pageCount);
	GoodsCategory getGoodsCategoryById(Integer id);
}
