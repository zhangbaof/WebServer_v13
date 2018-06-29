package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.bean.Cart;

public interface CartMapper {
	void add(Cart cart);
	List<Cart> getCartList(Integer uid);
	
}
