package com.dsam.assignment01.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@NamedEntityGraph(name = "User.orders", attributeNodes = {@NamedAttributeNode(value = "orders")})
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;

	@NotNull(message = "Username can not be null")
	@NotEmpty(message = "Username can not be empty")
	@Column(name = "username")
	private String username;

	@NotNull(message = "Password can not be null")
	@NotEmpty(message = "Password can not be empty")
	@Column(name = "password")
	private String password;

	@NotNull(message = "Full name can not be null")
	@NotEmpty(message = "Full name can not be empty")
	@Column(name = "full_name")
	private String fullName;

	@Email
	@NotNull(message = "Email can not be null")
	@NotEmpty(message = "Email can not be empty")
	@Column(name = "email")
	private String email;

	@Column(name = "role")
	private String role;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Order> orders;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username="
			+ username + ", fullName=" + fullName + ", email="
			+ email + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(userId)
			.append(username)
			.append(fullName)
			.append(email)
			.append(role)
			.hashCode();
	}
}
