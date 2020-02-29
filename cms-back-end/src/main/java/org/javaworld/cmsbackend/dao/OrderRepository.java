package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	public long countByDeliveryDateBetween(long startDate, long endDate);

	public Page<Order> findByDeliveryDateGreaterThanOrderByDeliveryDate(long currentTimeStamp, Pageable pageable);
	
	public long countByCreatedAtGreaterThanEqual(long startOfDayTimeStamp);
	
	public Page<Order> findByCreatedAtGreaterThanEqual(long startOfDayTimeStamp, Pageable pageable);

}
