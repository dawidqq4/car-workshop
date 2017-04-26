package entities.orderstates;

import entities.Order;

public class Tax4Percent implements TaxCalculator {

	@Override
	public double calculateTax(Order order) {
		double taxedPrice = order.getPrice();
		taxedPrice *= 1.04;
		return taxedPrice;
	}
}
