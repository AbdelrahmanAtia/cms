package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
