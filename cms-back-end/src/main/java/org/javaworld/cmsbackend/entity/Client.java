package org.javaworld.cmsbackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq_gen")
	@SequenceGenerator(sequenceName = "client_seq", allocationSize = 1, name = "client_seq_gen")
	@NotNull(groups = { OnUpdate.class })
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@NotBlank(groups = {OnUpdate.class, OnCreate.class})
	@Column(name = "name")
	private String name;

	@Email(groups = {OnCreate.class, OnUpdate.class})
	@NotBlank(groups = {OnCreate.class, OnUpdate.class})
	@Column(name = "email")
	private String email;

	@NotBlank(groups = {OnCreate.class, OnUpdate.class})
	@Column(name = "phone")
	private String phone;

	@Column(name = "company")
	private String company;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zip")
	private String zip;

	@Column(name = "country")
	private String country;

	@Column(name = "special_instructions")
	private String specialInstructions;

	@OneToOne(mappedBy = "client")
	private Order order;

	public Client() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Client [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", company=");
		builder.append(company);
		builder.append(", address=");
		builder.append(address);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", zip=");
		builder.append(zip);
		builder.append(", country=");
		builder.append(country);
		builder.append(", specialInstructions=");
		builder.append(specialInstructions);
		builder.append(", order=");
		builder.append(order.getId());
		builder.append("]");
		return builder.toString();
	}

}
