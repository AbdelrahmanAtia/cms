package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	public long countByDeliveryDateBetween(long startDate, long endDate);

	public Page<Order> findByDeliveryDateGreaterThan(long currentTimeStamp, Pageable pageable);

}
