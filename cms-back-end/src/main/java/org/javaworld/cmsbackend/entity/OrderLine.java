package org.javaworld.cmsbackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_line")
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Min(value = 1, groups = { OnCreate.class, OnUpdate.class }, message = "quantity must be greater than 0")
	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@Column(name = "quantity")
	private Integer quantity;
	
	@NotNull(groups = {OnCreate.class, OnUpdate.class})
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "price")
	private double price;

	@Column(name = "total_price")
	private double totalPrice;	

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public OrderLine() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@JsonIgnore
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice
				+ ", product=" + product + ", order=" + order + "]";
	}

}
