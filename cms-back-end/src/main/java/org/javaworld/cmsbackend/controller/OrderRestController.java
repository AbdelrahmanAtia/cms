package org.javaworld.cmsbackend.controller;

import java.util.List;

import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.entity.OrderLine;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List<Order> getOrders() {
		return orderService.findAll();
	}

	@GetMapping("/orders/{orderId}")
	public Order getOrder(@PathVariable int orderId) {
		return orderService.findById(orderId);
	}

	@PostMapping("/orders")
	public Order addOrder(@RequestBody Order order) {
		order.setId(0); // force creating a new entity
		order.getClient().setId(0); // force creating a new entity

		List<OrderLine> orderLines = order.getOrderLines();
		for (OrderLine orderline : orderLines) {
			orderline.setId(0); // force creating a new entity
			orderline.setOrder(order); // bidirectional relationship
		}

		orderService.save(order);
		return order;
	}
	
	@PutMapping("/orders")
	public Order updateOrder(@RequestBody Order order) {
		orderService.update(order);
		return order;
	}
	
	
	@DeleteMapping("/orders/{orderId}")
	public Response deleteOrder(@PathVariable int orderId) {
		return orderService.deleteOrder(orderId);		
	}
	

}
