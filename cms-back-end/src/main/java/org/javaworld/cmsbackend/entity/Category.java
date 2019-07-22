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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name; 

	@Column(name = "description") 
	private String description;

	@Lob
	@Column(name = "image")
	private byte[] image;
	
	
	/*

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, 
			   cascade = { CascadeType.PERSIST, CascadeType.MERGE,
					   	   CascadeType.REFRESH, CascadeType.DETACH })
	private List<Product> products;

	*/
	public Category() {
	}

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
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

	@JsonIgnore
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	/*
	@JsonIgnore
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void add(Product product) {
		if(products == null) {
			products = new ArrayList<>();
		}
		products.add(product);
		product.setCategory(this);
	}
	*/

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description
				+ "]";
	}

}
