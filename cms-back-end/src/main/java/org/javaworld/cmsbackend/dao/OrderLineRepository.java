package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

	@Modifying
	@Query("delete from OrderLine ol where ol.order.id = :orderId")
	public void deleteOrderlines(@Param("orderId") int orderId);

}
