package org.javaworld.cmsbackend.dao;

import java.util.Optional;

import org.javaworld.cmsbackend.entity.Client;
import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.model.OrderStatus;
import org.javaworld.cmsbackend.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository  orderRepository;
	
	//@Test
	public void testSaveOrder() {	
		Order order = new Order();
		order.setDeliveryDate(DateUtil.getCurrentDateTimeAsTimeStamp());	
		order.setTotalPrice(50.0);
		order.setTax(20.0);
		order.setPaymentMethod("Cash");
		order.setSubtotal(11.0);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		orderRepository.save(order);
	}
	
	
	@Test
	public void testUpdateOrder() {
		//Order order = getOrder();
		//orderRepository.save(order);
		
		
	}
	
	
	public Order getOrder() {
		

		
		Order order = new Order();
		order.setDeliveryDate(DateUtil.getCurrentDateTimeAsTimeStamp());	
		order.setTotalPrice(50.0);
		order.setTax(20.0);
		order.setPaymentMethod("Cash");
		order.setSubtotal(11.0);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		return order;
	}
	

}
