package org.javaworld.cmsbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.model.OrderStatus;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.service.OrderService;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders/all")
	public List<Order> getAllOrders() {
		return orderService.findAll();
	}

	@GetMapping("/orders")
	public List<Order> getOrders(@RequestParam OrderStatus orderStatus, 
			@RequestParam int pageNumber,
			@RequestParam int pageSize) {
		return orderService.getOrders(orderStatus, pageNumber, pageSize);
	}

	@GetMapping("/orders/{orderId}")
	public Order getOrder(@PathVariable int orderId) {
		return orderService.findById(orderId);
	}

	@PostMapping("/orders")
	public Order addOrder(@Validated(value = OnCreate.class) @RequestBody Order order) {
		return orderService.save(order);
	}

	@PutMapping("/orders")
	public Order updateOrder(@Validated(value = OnUpdate.class) @RequestBody Order order) {
		return orderService.update(order);
	}

	@DeleteMapping("/orders/{orderId}")
	public Response deleteOrder(@PathVariable int orderId) {
		return orderService.deleteOrder(orderId);
	}

	@GetMapping("/orders/nextToDeliver")
	public List<Order> getNextOrdersToBeDelivered() {
		return orderService.getNextOrdersToBeDelivered();
	}

	@GetMapping("/orders/receivedToday")
	public List<Order> getOrdersReceivedToday() {
		return orderService.getOrdersReceivedToday();
	}

	@GetMapping("/orders/orderStatusList")
	public List<OrderStatus> getOrderStatusList() {
		List<OrderStatus> orderStatusList = new ArrayList<>();
		for (OrderStatus orderStatus : OrderStatus.values()) {
			orderStatusList.add(orderStatus);
		}
		return orderStatusList;
	}

}
