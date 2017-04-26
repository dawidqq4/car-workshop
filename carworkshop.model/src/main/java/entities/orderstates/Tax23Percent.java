package entities.orderstates;

import entities.Order;

public class Tax23Percent implements TaxCalculator {

	@Override
	public double calculateTax(Order order) {
		double taxedPrice = order.getPrice();
		taxedPrice *= 1.23;
		return taxedPrice;
	}
}