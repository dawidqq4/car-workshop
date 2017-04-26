package entities.orderstates;

import java.io.Serializable;

import entities.Order;

public class InProgressState implements OrderState, Serializable  {
	private static final long serialVersionUID = -2275246432601455088L;

	@Override
	public String getDescription() {
		return "Your car is in repairing process. Please wait.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}

	@Override
	public void action(Order order) {	
		System.out.println("Employee: " + order.getWorker().getSurname() + " repairing your car.");
	}
}
