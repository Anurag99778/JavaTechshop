package entities;

import java.time.LocalDate;

public class Orders implements Comparable<Orders> {
	private Customers customer;
	private int orderId;
	private LocalDate orderDate;
	private String status;
	private double totalAmount;

	// New payment-related fields
	private String paymentMethod;
	private String paymentStatus;
	private LocalDate paymentDate;
	private double paymentAmount;

	public Orders() {
		super();
	}

	public Orders(Customers customer, int orderId, LocalDate orderDate, double totalAmount, String status,
				  String paymentMethod, String paymentStatus, LocalDate paymentDate, double paymentAmount) {
		super();
		this.customer = customer;
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Getters and Setters for new payment-related fields
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public String toString() {
		return "Orders [customer=" + customer.getFirstName() + " " + customer.getLastName() + ", orderId=" + orderId
				+ ", orderDate=" + orderDate + ", status=" + status + ", totalAmount=" + totalAmount
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + ", paymentDate="
				+ paymentDate + ", paymentAmount=" + paymentAmount + "]\n";
	}

	@Override
	public int compareTo(Orders otherOrder) {
		return this.orderDate.compareTo(otherOrder.getOrderDate());
	}
}
