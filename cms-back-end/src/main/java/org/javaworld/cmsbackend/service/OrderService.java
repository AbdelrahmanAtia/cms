package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.model.OrderStatus;
import org.javaworld.cmsbackend.model.Response;

public interface OrderService {

	public List<Order> findAll();

	public List<Order> getOrders(OrderStatus orderStatus, int pageNumber, int pageSize);

	public Order findById(int id);

	public Order save(Order order);

	public Order update(Order order);

	public Response deleteOrder(int orderId);

	public long getTotalOrdersCount();

	public long getOrdersToDeliverTodayCount();

	public List<Order> getNextOrdersToBeDelivered();

	public long getOrdersReceivedTodayCount();

	public List<Order> getOrdersReceivedToday();

}
