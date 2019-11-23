package org.javaworld.cmsbackend.service;

import java.util.List;
import java.util.Optional;

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
	public Order findById(int id) {
		Optional<Order> result = orderRepository.findById(id);
		Order order = null;
		if (result.isPresent())
			order = result.get();
		else
			throw new RuntimeException("Did not find order with id - " + id);

		return order;
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

}
