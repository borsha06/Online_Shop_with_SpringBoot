package com.dsam.assignment02.data;

import com.dsam.assignment02.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

	public Order getById(Long orderId) throws SQLException {
		String sqlQuery = getQuery(orderId);

		try (Connection connection = new CloudSqlDataSource().getConnection()) {
			PreparedStatement pst = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = pst.executeQuery();

			List<OrderItem> orderItems = new ArrayList<>();

			while (resultSet.next()) {
				OrderItem orderItem = getOrderItem(resultSet);
				orderItems.add(orderItem);
			}

			if (orderItems.isEmpty()) {
				return null;
			}

			// reset cursor
			resultSet.first();

			return getOrder(resultSet, orderItems);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private Order getOrder(ResultSet resultSet, List<OrderItem> orderItems) throws SQLException {
		User user = getUser(resultSet);

		Order order = new Order();
		order.setOrderId(resultSet.getLong("order_id"));
		order.setOrderPrice(resultSet.getDouble("order_price"));
		order.setUser(user);
		order.setOrderItems(orderItems);

		return order;
	}

	private User getUser(ResultSet resultSet) throws SQLException {
		Address address = new Address();
		address.setAddressId(resultSet.getLong("address_id"));
		address.setStreet(resultSet.getString("street"));
		address.setHouseNumber(resultSet.getString("house_number"));
		address.setPostalCode(resultSet.getString("postal_code"));
		address.setCity(resultSet.getString("city"));
		address.setState(resultSet.getString("state"));

		User user = new User();
		user.setUserId(resultSet.getLong("user_id"));
		user.setEmail(resultSet.getString("email"));
		user.setFullName(resultSet.getString("full_name"));
		user.setAddress(address);

		return user;
	}

	private OrderItem getOrderItem(ResultSet resultSet) throws SQLException {
		Beverage beverage = new Beverage();
		beverage.setBeverageId(resultSet.getLong("beverage_id"));
		beverage.setName(resultSet.getString("name"));
		beverage.setBeveragePrice(resultSet.getDouble("beverage_price"));

		OrderItem orderItem = new OrderItem();
		orderItem.setOrderItemId(resultSet.getLong("order_item_id"));
		orderItem.setOrderItemPrice(resultSet.getDouble("order_item_price"));
		orderItem.setPosition(resultSet.getInt("position"));
		orderItem.setQuantity(resultSet.getInt("quantity"));
		orderItem.setOrderItemBeverage(beverage);

		return orderItem;
	}

	private String getQuery(Long orderId) {
		return new StringBuilder()
			.append("select o.order_id, o.order_price, ")
			.append("u.user_id, u.email, u.full_name, ")
			.append("a.address_id, a.street, a.house_number, a.postal_code, a.city, a.state, ")
			.append("oi.order_item_id, oi.order_item_price, oi.position, oi.quantity, ")
			.append("b.beverage_id, b.name, b.beverage_price ")
			.append("from orders as o ")
			.append("inner join users_orders as ou on o.order_id = ou.orders_order_id ")
			.append("inner join users as u on ou.user_user_id = u.user_id ")
			.append("inner join addresses as a on a.address_id = u.address_id ")
			.append("inner join orders_order_items as ooi on o.order_id = ooi.order_order_id ")
			.append("inner join order_items as oi on oi.order_item_id = ooi.order_items_order_item_id ")
			.append("inner join beverages as b on b.beverage_id = oi.beverage_id ")
			.append("where o.order_id = ")
			.append(orderId.toString())
			.append(" order by oi.position asc;")
			.toString();
	}
}
