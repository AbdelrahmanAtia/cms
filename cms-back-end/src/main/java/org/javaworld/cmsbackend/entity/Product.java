package org.javaworld.cmsbackend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@NotNull(groups = { OnUpdate.class })
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(groups = { OnCreate.class, OnUpdate.class })
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@DecimalMin(value = "0.0", inclusive = false, groups = { OnCreate.class, OnUpdate.class })
	@Column(name = "price")
	private double price;

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@Type(type = "org.hibernate.type.BooleanType")
	@Column(name = "active")
	private Boolean active;

	@Column(name = "image")
	private String image; // base64 string

	@NotNull(groups = { OnCreate.class, OnUpdate.class })
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "category_id")
	private Category category;

	public Product() {

	}

	public Product(String name, String description, double price, boolean active) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", active=" + active + ", category=" + category + "]";
	}

}
