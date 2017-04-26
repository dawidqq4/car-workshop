package entities.orderstates;

import entities.Order;

public interface TaxCalculator {
	public double calculateTax(Order order);
}
