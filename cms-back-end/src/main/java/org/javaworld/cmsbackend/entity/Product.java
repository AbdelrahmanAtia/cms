package org.javaworld.cmsbackend.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private double price;

	@Type(type = "org.hibernate.type.BooleanType")
	@Column(name = "active")
	private boolean active;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@ManyToOne(fetch = FetchType.EAGER, 
			   cascade = { CascadeType.DETACH, CascadeType.MERGE, 
				           CascadeType.PERSIST,CascadeType.REFRESH })
	@JoinColumn(name = "category_id")
	private Category category;
 
	
	
	/*
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
			   cascade = { CascadeType.DETACH, CascadeType.MERGE,
			               CascadeType.PERSIST, CascadeType.REFRESH })
	private List<OrderLine> orderLines;

	*/
	public Product() {

	}

	public Product(String name, String description, double price, 
			       boolean active) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	/*
	@JsonIgnore
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	public void add(OrderLine orderLine) {
		if(this.orderLines == null) {
			orderLines = new ArrayList<>();
		}
		orderLines.add(orderLine);
		orderLine.setProduct(this);
	}
	*/

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", active=" + active + ", category=" + category + "]";
	}

}
