package entities.orderstates;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Order;

public class WerificationState implements OrderState, Serializable {
	private static final long serialVersionUID = 3314576364199943394L;

	@Override
	public String getDescription() {
		return "Order is in verification process. Please wait until worker change process for in repair.";
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
		if (order.getPrice() > 0) {
			System.out.println("Price for repair: " + String.format( "%.2f", order.getPrice()) + "\n");
			System.out.print("Do you accept this price?? Press [t/n]");
			String readedChoose = reader.nextLine();
			Matcher matcher = choosePattern.matcher(readedChoose);
			if (matcher.matches() && readedChoose.equals("t")) {
				order.setStatus("inprogress");
				order.setState(new InProgressState());
			} 
			if (matcher.matches() && readedChoose.equals("n")) {
				order.setStatus("closed");
				order.setState(new ClosedState());
			} 
		}
		System.out.print("Need you phone to the secretary?? Press [t/n]");
		String readedChoose = reader.nextLine();
		Matcher matcher = choosePattern.matcher(readedChoose);
		if (matcher.matches() && readedChoose.equals("t"))
			System.out.println("Secretary phone: " + order.getWorker().getPhone());
	}
}
