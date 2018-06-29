package cn.tedu.store.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.bean.GoodsCategory;
import cn.tedu.store.mapper.GoodsCategoryMapper;
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements GoodsCategoryService{

	@Resource
	private GoodsCategoryMapper goodsCategoryMapper;
	
	public List<GoodsCategory> getGoodsCategoryList(Integer page) {
		page--;
		if(page<0) {
			page=0;
		}
		Integer offset = page*30;
		return goodsCategoryMapper.getGoodsCategoryList(offset);
	}
	public List<GoodsCategory> getGoodsCategoryByParent(Integer parentId, Integer offset, Integer pageCount) {
		return goodsCategoryMapper.getGoodsCategoryByParent(parentId, offset, pageCount);
	}
	public GoodsCategory getGoodsCategoryById(Integer id) {
		return goodsCategoryMapper.getGoodsCategoryById(id);
	}

}
