package org.javaworld.cmsbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.javaworld.cmsbackend.dao.OrderLineRepository;
import org.javaworld.cmsbackend.dao.OrderRepository;
import org.javaworld.cmsbackend.dao.ProductRepository;
import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.entity.OrderLine;
import org.javaworld.cmsbackend.entity.Product;
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

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrders(OrderStatus orderStatus, int pageNumber, int pageSize) {

		pageNumber--; // paging is 0 indexed

		Page<Order> page = null;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (orderStatus == OrderStatus.ALL) {
			page = orderRepository.findAll(pageable);
		} else {
			page = orderRepository.findByOrderStatus(orderStatus, pageable);
		}

		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());
		return page.hasContent() ? page.getContent() : new ArrayList<>();

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
	@Transactional
	public Order save(Order order) {
		order.setId(0); // force creating a new entity
		order.getClient().setId(0); // force creating a new entity
		order.setCreatedAt(DateUtil.getCurrentDateTimeAsTimeStamp());
		setOrderFields(order);
		return orderRepository.save(order);
	}

	@Override
	@Transactional
	public Order update(Order order) {
		
		// delete all order lines for the given order
		orderLineRepository.deleteOrderlines(order.getId());
		
		setOrderFields(order);
		
		//set createdAt field to the old value
		Optional<Order> optionalOrder= orderRepository.findById(order.getId());
		Order tempOrder = optionalOrder.get();
		if(tempOrder != null) {
			order.setCreatedAt(tempOrder.getCreatedAt());
		}
		
		return orderRepository.save(order);
	}

	@Override
	@Transactional
	public Response deleteOrder(int orderId) {
		try {
			orderRepository.deleteById(orderId);
		} catch (EmptyResultDataAccessException ex) {
			return new Response(false, "Order id not found - " + orderId);
		}
		return new Response(true, "Deleted order id - " + orderId);
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
		Page<Order> ordersPage = orderRepository.findByDeliveryDateGreaterThanOrderByDeliveryDate(currentTimeStamp,
				pageable);
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

	/**
	 * creates bi directional relationship between order and order lines,<br>
	 * calculates  and sets the following order fields [sub total, tax, total]
	 * @param order
	 */
	private void setOrderFields(Order order) {
		List<OrderLine> orderLines = order.getOrderLines();
		double subTotal = 0.0;
		for (OrderLine orderline : orderLines) {
			
			orderline.setId(0); // force creating a new entity
			orderline.setOrder(order); // bidirectional relationship
			
			int productId = orderline.getProduct().getId();
			double productPrice = this.getProductPriceById(productId);
			int qty = orderline.getQuantity();
			
			//we set those fields in data base because product price may change 
			//so we need to have the old product price when the order was made
			orderline.setPrice(productPrice);
			orderline.setTotalPrice(productPrice * qty);
			
			subTotal = subTotal + productPrice * orderline.getQuantity();
		}

		double tax = 0.1 * subTotal;
		double totalPrice = subTotal + tax;

		order.setSubtotal(subTotal);
		order.setTax(tax);
		order.setTotalPrice(totalPrice);
	}

	public double getProductPriceById(int productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Product tempProd = optionalProduct.get();
		return (tempProd != null) ? tempProd.getPrice() : 0;
	}

}
