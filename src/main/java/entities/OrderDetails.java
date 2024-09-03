package entities;

public class OrderDetails {
	private int orderDetailsid;
	private Orders order;
	private Products product;
	private int quantity;

	public OrderDetails() {
		super();
	}

	public OrderDetails(int orderDetailsid, Orders order, Products product, int quantity) {
		super();
		this.orderDetailsid = orderDetailsid;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public int getOrderDetailsid() {
		return orderDetailsid;
	}

	public void setOrderDetailsid(int orderDetailsid) {
		this.orderDetailsid = orderDetailsid;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderDetailsid=" + orderDetailsid + ", order=" + order + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}

}
