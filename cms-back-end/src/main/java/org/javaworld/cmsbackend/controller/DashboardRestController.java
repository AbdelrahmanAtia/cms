package org.javaworld.cmsbackend.controller;

import org.javaworld.cmsbackend.model.DashBoardInfo;
import org.javaworld.cmsbackend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardRestController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("dashboard/dashboardInfo")
	public DashBoardInfo getDashboardInfo() {
		return dashboardService.getDashBoardInfo();
	}

}
