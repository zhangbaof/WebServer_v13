package cn.tedu.store.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tedu.store.bean.Address;
import cn.tedu.store.bean.Cart;
import cn.tedu.store.bean.Order;
import cn.tedu.store.bean.OrderItem;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.OrderService;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

	@Resource 
	private AddressService addressService;
	
	@Resource 
	private CartService cartService;
	
	@Resource 
	private OrderService orderService;
	
	@RequestMapping("/list.do")
	public String showOrderList(HttpSession session,ModelMap modelMap) {
		Integer uid = getUidFromSession(session);
		Address recvList = addressService.getAddressById(13,uid);
		List<Cart> cartList = cartService.getCartList(uid);
		modelMap.addAttribute("cartsItems",cartList);
		modelMap.addAttribute("recvList",recvList);
		return "orderConfirm";
	}
	
	@RequestMapping("/orderReplay.do")
	public String orderReplay(HttpSession session,@RequestParam("id")Integer id,ModelMap modelMap) {
		Integer uid = getUidFromSession(session);
		List<Cart> cartList = cartService.getCartList(uid);
		Address recvList = addressService.getAddressById(id,uid);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		Integer num = 0;
		for(Cart cart:cartList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setGoodsImage(cart.getGoodsImage());
			orderItem.setGoodsPrice(cart.getGoodsPrice());
			orderItem.setNum(cart.getNum());
			orderItem.setGoodsTitle(cart.getGoodsTitle());
			orderItem.setGoodsId(cart.getGoodsId());
			orderItem.setOrderId(null);
			num = num+cart.getGoodsPrice()*cart.getNum();
			orderItemList.add(orderItem);
		}
		Order order = new Order();
		order.setRecvAddr(recvList.getRecvAddr());
		order.setRecvAddrCode(recvList.getRecvAddrCode());
		order.setRecvDistrict(recvList.getRecvDistrict());
		order.setRecvPerson(recvList.getRecvPerson());
		order.setUserId(uid);
		order.setRecvPhone(recvList.getRecvPhone());
		orderService.createOrder(order, orderItemList);
		modelMap.addAttribute("recvList",recvList);
		modelMap.addAttribute("totle",num);
		System.out.println(num);
		return "payment";
	}
	@RequestMapping("/orderList.do")
	public String orderList(HttpSession session,ModelMap modelMap) {
		Integer uid = getUidFromSession(session);
		List<Integer> userIdList = orderService.selectOrderId(uid);
		if(userIdList==null) {
			return "orders";
		}
		List<List<OrderItem>> list = new ArrayList<List<OrderItem>>();
		for(Integer id:userIdList) {
			List<OrderItem> orderItemList = orderService.showOrderItems(id);
			list.add(orderItemList);
		}
		
		System.out.println(list);
		modelMap.addAttribute("list",list);
		return "orders";
	}
	
	
	
}
