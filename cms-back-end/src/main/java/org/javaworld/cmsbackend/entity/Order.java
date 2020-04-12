package org.javaworld.cmsbackend.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.javaworld.cmsbackend.model.OrderStatus;
import org.javaworld.cmsbackend.validator.AfterNow;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;

@Entity
@Table(name = "client_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_order_seq_gen")
	@SequenceGenerator(sequenceName = "client_order_seq", allocationSize = 1, name = "client_order_seq_gen")
	@NotNull(groups = { OnUpdate.class })
	@Column(name = "id")
	private Integer id;

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@AfterNow(groups = { OnCreate.class })
	@Column(name = "delivery_date")
	private Long deliveryDate;

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name = "subtotal")
	private double subtotal;

	@Column(name = "tax")
	private double tax;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "ip_address")
	private String ipAddress;

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Column(name = "payment_method")
	private String paymentMethod;

	@Valid
	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "client_id")
	private Client client;

	@Valid
	@Size(min = 1, groups = {OnCreate.class, OnUpdate.class})
	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<OrderLine> orderLines;

	@Column(name = "created_at")
	private long createdAt;

	public Order() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Long deliveryDate) {
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", deliveryDate=");
		builder.append(deliveryDate);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", subtotal=");
		builder.append(subtotal);
		builder.append(", totalPrice=");
		builder.append(totalPrice);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", clientId=");
		builder.append(client.getId());
		builder.append(", orderLinesSize=");
		builder.append(orderLines.size());
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}

}
