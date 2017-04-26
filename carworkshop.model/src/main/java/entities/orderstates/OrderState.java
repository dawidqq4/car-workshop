package entities.orderstates;

import entities.Order;

public interface OrderState {
	public String getDescription();
	public boolean checkCancelPossibility();
	public void action(Order order);
}
