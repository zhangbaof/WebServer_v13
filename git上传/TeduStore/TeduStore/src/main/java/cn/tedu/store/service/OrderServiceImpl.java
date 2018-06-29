package cn.tedu.store.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.bean.Order;
import cn.tedu.store.bean.OrderItem;
import cn.tedu.store.mapper.OrderMapper;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderMapper orderMapper;
	
	public void addOrder(Order order) {
		orderMapper.addOrder(order);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderMapper.addOrderItem(orderItem);
	}

	@Transactional
	public void createOrder(Order order, List<OrderItem> orderItems) {
		//第1步：增加订单表中的数据
		System.out.println(order);
		System.out.println(orderItems);
		this.addOrder(order);
		//第1步：获取刚增加的数据的id
		Integer orderId = order.getId();
		//第2步：增加订单商品表中的数据
		for(int i = 0;i<orderItems.size();i++) {
			orderItems.get(i).setOrderId(orderId);
			this.addOrderItem(orderItems.get(i));
		}
	}

	public List<Integer> selectOrderId(Integer userId) {
		
		return orderMapper.selectOrderId(userId);
	}

	public List<OrderItem> showOrderItems(Integer orderId) {
		return orderMapper.showOrderItems(orderId);
	}

}
