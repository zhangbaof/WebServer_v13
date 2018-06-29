package cn.tedu.store.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.store.bean.Goods;
import cn.tedu.store.mapper.GoodsMapper;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

	@Resource
	private GoodsMapper goodsMapper;
	public List<Goods> getGoodsList(Integer page) {
		if(--page<0) {
			page = 0;
		}
		Integer offset = page*30;
		return goodsMapper.getGoodsList(offset);
	}
	
	public List<Goods> getGoodsByCategoryId(Integer categoryId, Integer offset, String orderBy,Integer pageCount) {
		return goodsMapper.getGoodsByCategoryId(categoryId, offset,orderBy,pageCount);
	}

	public Integer getRecordCountByCategoryId(Integer categoryId) {
		return goodsMapper.getRecordCountByCategoryId(categoryId);
	}

	public Goods getGoodsById(Integer id) {
		return goodsMapper.getGoodsById(id);
	}

	public List<Goods> getGoodsListByItemType(String itemType) {
		return goodsMapper.getGoodsListByItemType(itemType);
	}

	

}
