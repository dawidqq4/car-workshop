package entities.orderstates;

import entities.Order;

public class Tax5Percent implements TaxCalculator {

	@Override
	public double calculateTax(Order order) {
		double taxedPrice = order.getPrice();
		taxedPrice *= 1.05;
		return taxedPrice;
	}
}
