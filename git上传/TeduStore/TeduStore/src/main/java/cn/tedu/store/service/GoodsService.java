package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.bean.Goods;

public interface GoodsService {

	List<Goods> getGoodsList(Integer page);
	@Deprecated
	List<Goods> getGoodsByCategoryId(Integer categoryId,Integer offset,String orderBy,Integer pageCount);
	Integer getRecordCountByCategoryId(Integer categoryId);
	Goods getGoodsById(Integer id);
	List<Goods> getGoodsListByItemType(String itemType);
}
