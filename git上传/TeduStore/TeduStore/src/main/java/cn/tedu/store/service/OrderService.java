package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.bean.Order;
import cn.tedu.store.bean.OrderItem;

public interface OrderService {

	void addOrder(Order order);
	void addOrderItem(OrderItem orderItem);
	void createOrder(Order order,List<OrderItem> orderItems);
	List<Integer> selectOrderId(Integer userId);
	List<OrderItem> showOrderItems(Integer orderId);
	
}
