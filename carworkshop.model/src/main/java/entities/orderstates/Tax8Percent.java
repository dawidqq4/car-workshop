package entities.orderstates;

import entities.Order;

public class Tax8Percent implements TaxCalculator {

	@Override
	public double calculateTax(Order order) {
		double taxedPrice = order.getPrice();
		taxedPrice *= 1.08;
		return taxedPrice;
	}
}
