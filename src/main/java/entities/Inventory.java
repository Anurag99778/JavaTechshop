package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private int inventoryId;
	private Products product;
	private int quantityInStock;
	private LocalDate lastStockUpdate;
	List<Products> products=new ArrayList<Products>();
	
	
	
	public Inventory() {
		super();
	}

	public Inventory(int inventoryId, Products product, int quantityInStock, LocalDate lastStockUpdate) {
		super();
		this.inventoryId = inventoryId;
		this.product = product;
		this.quantityInStock = quantityInStock;
		this.lastStockUpdate = lastStockUpdate;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public LocalDate getLastStockUpdate() {
		return lastStockUpdate;
	}

	public void setLastStockUpdate(LocalDate lastStockUpdate) {
		this.lastStockUpdate = lastStockUpdate;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", product=" + product + ", quantityInStock=" + quantityInStock
				+ ", lastStockUpdate=" + lastStockUpdate + "]";
	}
	
}