package org.javaworld.cmsbackend.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "delivery_date")
	private long deliveryDate;

	@Column(name = "tax")
	private double tax;

	@Column(name = "subtotal")
	private double subtotal;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "status")
	private String status;

	@Column(name = "payment_method")
	private String paymentMethod;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<OrderLine> orderLines;

	public Order() {

	}

	public Order(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(long deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", deliveryDate=" + deliveryDate + ", tax=" + tax + ", subtotal=" + subtotal
				+ ", totalPrice=" + totalPrice + ", ipAddress=" + ipAddress + ", status=" + status + ", paymentMethod="
				+ paymentMethod + "]";
	}

}
