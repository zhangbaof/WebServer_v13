package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;

public interface GoodsMapper {
	List<Goods> getGoodsList(Integer offset);
	List<Goods> getGoodsByCategoryId(
			@Param("categoryId")Integer categoryId,
			@Param("offset")Integer offset,
			@Param("orderBy")String orderBy,
			@Param("pageCount")Integer pageCount
			
			);
	
	
	Integer getRecordCountByCategoryId(Integer categoryId);
	Goods getGoodsById(Integer id);
	List<Goods> getGoodsListByItemType(String itemType);
	
	
	
}
