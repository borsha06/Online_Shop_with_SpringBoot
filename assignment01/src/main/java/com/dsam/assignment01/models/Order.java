package com.dsam.assignment01.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(name="Order.orderItems", attributeNodes = {@NamedAttributeNode(value = "orderItems")})
@Table(name = "orders")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long orderId;

	@Positive(message = "Price must be positive")
	@Column(name = "order_price")
	private Double orderPrice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<OrderItem> orderItems;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderPrice=" + orderPrice + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(orderId)
			.append(orderPrice)
			.hashCode();
	}

	public void removeOrderItem(OrderItem orderItem) {
		orderItems.remove(orderItem);
		Double totalOrderPrice = 0.0;
		for (OrderItem item: getOrderItems()) {
			totalOrderPrice += item.getOrderItemPrice();
		}
		orderPrice = totalOrderPrice;
	}

	public OrderItem findOrderItemByBeverageId(Long beverageId) {
		return orderItems.stream()
			.filter(item -> Objects.equals(item.getOrderItemBeverage().getBeverageId(), beverageId))
			.findFirst()
			.orElse(null);
	}

	public void addOrderItem(OrderItem orderItem) {
		if (orderItems == null) {
			orderItems = new HashSet<>();
		}

		orderItems.add(orderItem);
		orderPrice += orderItem.getOrderItemPrice();
	}

	public static Order createEmpty() {
		Order order = new Order();
		order.setOrderPrice(0.0);
		order.setOrderItems(new HashSet<>());
		return order;
	}
}
