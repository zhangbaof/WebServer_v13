package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;

public interface GoodsCategoryMapper {
	//offset偏移量
	List<GoodsCategory> getGoodsCategoryList(Integer offset);
	List<GoodsCategory> getGoodsCategoryByParent(
			@Param("parentId")Integer parentId,
			@Param("offset")Integer offset,
			@Param("pageCount")Integer pageCount);
	GoodsCategory getGoodsCategoryById(Integer id);

	
	
}
