package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.dao.OrderRepository;
import org.javaworld.cmsbackend.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

}
