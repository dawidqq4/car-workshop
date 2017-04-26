package entities.orderstates;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Facture;
import entities.Order;

public class FinishedState implements OrderState, Serializable {
	private static final long serialVersionUID = 1567624129207979621L;

	@Override
	public String getDescription() {
		return "Order was finished. Your car is waiting for you.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}

	@Override
	public void action(Order order) {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("We need your tax, please enter[0,4,5,8,23]: ");
		Pattern choosePattern = Pattern.compile("[0458]{1}");
		Pattern choosePattern23 = Pattern.compile("[2][3]{1}");
		Matcher matcher;
		Matcher matcher23;
		String readedChoose;
		do {
			readedChoose = reader.nextLine();
			matcher = choosePattern.matcher(readedChoose);
			matcher23 = choosePattern23.matcher(readedChoose);
		} while (matcher.matches() == false && matcher23.matches() == false);
		double price = 0;
		Context context = new Context(new Tax0Percent());
		switch (Integer.parseInt(readedChoose)) {
		case 0:
			price = context.executeStrategy(order);
			break;
		case 4:
			context.setStrategy(new Tax4Percent());
			price = context.executeStrategy(order);
			break;
		case 5:
			context.setStrategy(new Tax5Percent());
			price = context.executeStrategy(order);
			break;
		case 8:
			context.setStrategy(new Tax8Percent());
			price = context.executeStrategy(order);
			break;
		case 23:
			context.setStrategy(new Tax23Percent());
			price = context.executeStrategy(order);
			break;
		}
		System.out.println("You must pay: " + String.format("%.2f", price));
		System.out.print("Do you need facture?? Press [t/n]");
		readedChoose = reader.nextLine();
		if (matcher.matches() && readedChoose.equals("t")) {
			printFacture(order, price);
			order.setStatus("inprogress");
			order.setState(new InProgressState());
		}
		System.out.println("Thank you and see you again!");
		order.setStatus("closed");
		order.setState(new ClosedState());
	}
	
	private void printFacture(Order order, double price) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		Facture facture = new Facture(dtf.format(now));
		facture.setDescription("Company repair car: " + order.getCar().getVin() + " for client: " + order.getCar().getOwner().getSurname());
		facture.setOwner(order.getCar().getOwner());
		facture.setPrice(price);
		System.out.println(facture);
	}
}
 