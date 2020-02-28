package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.model.Response;

public interface OrderService {

	public List<Order> findAll();

	public Order findById(int id);

	public void save(Order order);

	public void update(Order order);

	public Response deleteOrder(int orderId);

	public long getTotalOrdersCount();

	public long getOrdersToDeliverTodayCount();

	public List<Order> getNextOrdersToBeDelivered();

}
