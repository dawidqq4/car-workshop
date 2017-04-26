package entities.orderstates;

import entities.Order;

public class Tax0Percent implements TaxCalculator {

	@Override
	public double calculateTax(Order order) {
		return order.getPrice();
	}
}
