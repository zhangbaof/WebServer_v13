package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.bean.Order;
import cn.tedu.store.bean.OrderItem;

public interface OrderMapper {

	void addOrder(Order order);
	void addOrderItem(OrderItem orderItem);
	List<Integer> selectOrderId(Integer userId);
	List<OrderItem> showOrderItems(Integer orderId);
}
