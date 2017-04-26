package entities.orderstates;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Order;

public class ClosedState implements OrderState, Serializable  {
	private static final long serialVersionUID = -4398148562157450163L;

	@Override
	public String getDescription() {
		return "Your order is closed.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}

	@Override
	public void action(Order order) {	
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[tn]{1}");
		System.out.print("Need you phone to the company?? Press [t/n]");
		String readedChoose = reader.nextLine();
		Matcher matcher = choosePattern.matcher(readedChoose);
		if (matcher.matches() && readedChoose.equals("t"))
			System.out.println("Secretary phone: " + order.getWorker().getPhone());
	}
}
