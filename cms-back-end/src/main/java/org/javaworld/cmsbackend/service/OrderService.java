package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Order;

public interface OrderService {

	public List<Order> findAll();

	public void save(Order order);

}
