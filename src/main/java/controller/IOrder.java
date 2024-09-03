package controller;

import java.util.List;

import entities.Orders;

public interface IOrder {
	public void putOrdersToArray();

	public void addOrder();

	public void getOrderDetails(int oid);

	public void showAllOrders();

	public void getOrderStatus(int orderId);

	public void updateOrderStatus(int oid, String status);

	public void cancelOrder(int oid);

	public void calculateTotalAmount(int oid);

	public List<Orders> getOrderList();
}
