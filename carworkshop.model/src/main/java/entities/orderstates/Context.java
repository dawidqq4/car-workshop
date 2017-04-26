package entities.orderstates;

import entities.Order;

public class Context {
	private TaxCalculator strategy;

	public Context(TaxCalculator strategy) {
		this.strategy = strategy;
	}
	
	public void setStrategy(TaxCalculator strategy) {
		this.strategy = strategy;
	}

	public double executeStrategy(Order order) {
		return strategy.calculateTax(order);
	}
}
