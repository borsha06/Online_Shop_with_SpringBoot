package com.dsam.assignment01.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_item_id")
	private Long orderItemId;

	@Column(name = "position")
	private Integer position;

	@Positive(message = "Price must be positive")
	@Column(name = "order_item_price")
	private Double orderItemPrice;

	@Positive(message = "Quantity must be positive")
	@Column(name = "quantity")
	private Integer quantity;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "beverage_id")
	private Beverage orderItemBeverage;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private Order order;

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", position="
			+ position + ", orderItemPrice=" + orderItemPrice + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(orderItemId)
			.append(position)
			.append(orderItemPrice)
			.append(quantity)
			.hashCode();
	}

	public void incrementQuantity() {
		setQuantity(getQuantity() + 1);
		setOrderItemPrice(getOrderItemBeverage().getBeveragePrice() * getQuantity());
	}
}
