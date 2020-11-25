/*package org.javaworld.cmsbackend.service;

import java.util.Optional;

import org.javaworld.cmsbackend.dao.OrderRepository;
import org.javaworld.cmsbackend.entity.Order;
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
public class OrderServiceImplTest {
	
	@Autowired
	private OrderService orderServiceImpl;
	
	@Autowired 
	private OrderRepository orderRepository;
	
	@Test
	public void updateTest() {
		
		Optional<Order> optionalOrder = orderRepository.findById(5);
		Order temp = optionalOrder.get();
		System.out.println("client id = " + temp.getClient().getId());
		temp.getClient().setId(null);
		orderServiceImpl.save(temp);
	}

}
*/