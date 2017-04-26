package entities.orderstates;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Order;

public class NewState implements OrderState, Serializable {
	private static final long serialVersionUID = 8049954524587627733L;

	@Override
	public String getDescription() {
		return "Order was added, please visit application for two days to apply price and start repair.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return true;
	}

	@Override
	public void action(Order order) {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[tn]{1}");
		System.out.print("Need you phone to the secretary?? Press [t/n]");
		String readedChoose = reader.nextLine();
		Matcher matcher = choosePattern.matcher(readedChoose);
		if (matcher.matches() && readedChoose.equals("t"))
			System.out.println("Secretary phone: " + order.getWorker().getPhone());
	}
}
