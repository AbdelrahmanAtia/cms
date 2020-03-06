package org.javaworld.cmsbackend.dao;

import java.util.List;

import org.javaworld.cmsbackend.entity.Order;
import org.javaworld.cmsbackend.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	public List<Order> findByOrderStatus(OrderStatus orderStatus);

	public long countByDeliveryDateBetween(long startDate, long endDate);

	public Page<Order> findByDeliveryDateGreaterThanOrderByDeliveryDate(long currentTimeStamp, Pageable pageable);

	public long countByCreatedAtGreaterThanEqual(long startOfDayTimeStamp);

	public Page<Order> findByCreatedAtGreaterThanEqual(long startOfDayTimeStamp, Pageable pageable);

}
