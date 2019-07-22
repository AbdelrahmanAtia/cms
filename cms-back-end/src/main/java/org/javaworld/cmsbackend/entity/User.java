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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;
	
	@Column(name="account_non_locked")
	private boolean accountNonLocked;

	@ManyToMany(fetch = FetchType.EAGER, 
			    cascade = { CascadeType.PERSIST, CascadeType.DETACH, 
			    		    CascadeType.MERGE, CascadeType.REFRESH })

	@JoinTable(
				name = "user_authority", 
				joinColumns = @JoinColumn(name = "user_id"), 
	            inverseJoinColumns = @JoinColumn(name = "authority_id")
			  )
	private List<Authority> authorities;

	public User() {
		// initialize member variables
		accountNonLocked = true;
	}	

	public User(String username, String password, String email, String phone, boolean accountNonLocked) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.accountNonLocked = accountNonLocked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}	

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		if (authorities == null) {
			authorities = new ArrayList<Authority>();
		}

		authorities.add(authority);
	}
	
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone="
				+ phone + ", accountNonLocked=" + accountNonLocked + ", authorities=" + authorities + "]";
	}

}