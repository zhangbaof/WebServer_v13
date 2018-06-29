package cn.tedu.store.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.store.bean.Cart;
import cn.tedu.store.mapper.CartMapper;


@Service("cartService")
public class CartServiceImpl implements CartService{

	@Resource
	private CartMapper cartMapper;
	
	public void add(Cart cart) {
		cartMapper.add(cart);
	}

	public List<Cart> getCartList(Integer uid) {
		return cartMapper.getCartList(uid);
	}

}
