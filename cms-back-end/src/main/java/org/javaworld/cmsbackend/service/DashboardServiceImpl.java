package org.javaworld.cmsbackend.service;

import org.javaworld.cmsbackend.model.DashBoardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OrderService orderService;

	@Override
	public DashBoardInfo getDashBoardInfo() {
		DashBoardInfo dashBoardInfo = new DashBoardInfo();
		dashBoardInfo.setTotalOrdersCount(orderService.getTotalOrdersCount());
		dashBoardInfo.setOrdersToDeliverTodayCount(orderService.getOrdersToDeliverTodayCount());
		return dashBoardInfo;
	}

}
