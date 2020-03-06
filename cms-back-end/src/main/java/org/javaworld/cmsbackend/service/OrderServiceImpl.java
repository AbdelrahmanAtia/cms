package org.javaworld.cmsbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.OrderLineRepository;
import org.javaworld.cmsbackend.dao.OrderRepository;
import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.entity.OrderLine;
import org.javaworld.cmsbackend.model.OrderStatus;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	@Override
	public List<Order> getOrders(OrderStatus orderStatus) {
		if(orderStatus == OrderStatus.ALL) {
			return orderRepository.findAll();
		}
		return orderRepository.findByOrderStatus(orderStatus);
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

		order.setId(0); // force creating a new entity
		order.getClient().setId(0); // force creating a new entity
		order.setCreatedAt(DateUtil.getCurrentDateTimeAsTimeStamp());

		List<OrderLine> orderLines = order.getOrderLines();
		for (OrderLine orderline : orderLines) {
			orderline.setId(0); // force creating a new entity
			orderline.setOrder(order); // bidirectional relationship
		}

		orderRepository.save(order);
	}

	@Override
	@Transactional
	public void update(Order order) {
		// delete all order lines for the given order
		orderLineRepository.deleteOrderlines(order.getId());
		List<OrderLine> orderLines = order.getOrderLines();
		for (OrderLine orderline : orderLines) {
			orderline.setId(0); // force creating a new entity
			orderline.setOrder(order); // bidirectional relationship
		}
		orderRepository.save(order);
	}

	@Override
	@Transactional
	public Response deleteOrder(int orderId) {
		try {
			orderRepository.deleteById(orderId);
		} catch (EmptyResultDataAccessException ex) {
			return new Response(Constants.NOT_FOUND_STATUS, "Order id not found - " + orderId);
		}
		return new Response(Constants.OK_STATUS, "Deleted order id - " + orderId);
	}

	@Override
	public long getTotalOrdersCount() {
		return orderRepository.count();
	}

	@Override
	public long getOrdersToDeliverTodayCount() {
		long startOfDay = DateUtil.getStartOfTheDayAsTimeStamp();
		long endOfDay = DateUtil.getEndOfTheDayAsTimeStamp();
		return orderRepository.countByDeliveryDateBetween(startOfDay, endOfDay);
	}

	@Override
	public List<Order> getNextOrdersToBeDelivered() {
		long currentTimeStamp = DateUtil.getCurrentDateTimeAsTimeStamp();
		Pageable pageable = PageRequest.of(0, 3); // the first 3 orders only
		Page<Order> ordersPage = orderRepository.findByDeliveryDateGreaterThanOrderByDeliveryDate(currentTimeStamp, pageable);
		return ordersPage.hasContent() ? ordersPage.getContent() : new ArrayList<>();
	}

	@Override
	public long getOrdersReceivedTodayCount() {
		long startOfDayTimeStamp = DateUtil.getStartOfTheDayAsTimeStamp();
		return orderRepository.countByCreatedAtGreaterThanEqual(startOfDayTimeStamp);
	}

	@Override
	public List<Order> getOrdersReceivedToday() {
		long startOfDayTimeStamp = DateUtil.getStartOfTheDayAsTimeStamp();
		Pageable pageable = PageRequest.of(0, 3);
		Page<Order> ordersPage = orderRepository.findByCreatedAtGreaterThanEqual(startOfDayTimeStamp, pageable);
		return ordersPage.hasContent() ? ordersPage.getContent() : new ArrayList<>();
	}

	

}
