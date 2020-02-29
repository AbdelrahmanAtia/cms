package org.javaworld.cmsbackend.model;

public class DashBoardInfo {

	private long totalOrdersCount;
	private long ordersToDeliverTodayCount;
	private long ordersReceivedTodayCount;

	public DashBoardInfo() {

	}

	public long getTotalOrdersCount() {
		return totalOrdersCount;
	}

	public void setTotalOrdersCount(long totalOrdersCount) {
		this.totalOrdersCount = totalOrdersCount;
	}

	public long getOrdersToDeliverTodayCount() {
		return ordersToDeliverTodayCount;
	}

	public void setOrdersToDeliverTodayCount(long ordersToDeliverTodayCount) {
		this.ordersToDeliverTodayCount = ordersToDeliverTodayCount;
	}

	public long getOrdersReceivedTodayCount() {
		return ordersReceivedTodayCount;
	}

	public void setOrdersReceivedTodayCount(long ordersReceivedTodayCount) {
		this.ordersReceivedTodayCount = ordersReceivedTodayCount;
	}

	@Override
	public String toString() {
		return "DashBoardInfo [totalOrdersCount=" + totalOrdersCount + ", ordersToDeliverTodayCount="
				+ ordersToDeliverTodayCount + ", ordersReceivedTodayCount=" + ordersReceivedTodayCount + "]";
	}

}
