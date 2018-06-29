package cn.tedu.store.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.bean.Cart;
import cn.tedu.store.bean.ResponseResult;
import cn.tedu.store.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

	@Resource
	private CartService cartService;
	
	@RequestMapping(value="/add.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleAddCart(Cart cart,HttpSession session){
		ResponseResult<Void> result = new ResponseResult<Void>();
		Integer uid = getUidFromSession(session);
		cart.setUserId(uid);
		cartService.add(cart);
		result.setState(ResponseResult.STATE_OK);
		result.setMessage("添加成功");
		return result;
	}
	@RequestMapping("/list.do")
	public String showCartList(HttpSession session,ModelMap modelMap) {
		
		Integer uid = getUidFromSession(session);
		List<Cart> list = cartService.getCartList(uid);
		modelMap.addAttribute("carts",list);
		return "cart_list";
	}
	
	
	
}
