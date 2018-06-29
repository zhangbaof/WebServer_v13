package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.bean.Cart;

public interface CartService {

	void add(Cart cart);
	List<Cart> getCartList(Integer uid);
}
