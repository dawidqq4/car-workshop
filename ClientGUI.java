package carworkshop.client.gui;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import carworkshop.client.OrderBuilder;
import carworkshop.client.TcpConnectionClient;
import entities.Order;
import entities.orderstates.ClosedState;


public class ClientGUI extends GUI {
	
	public ClientGUI() {	
	}
	
	
	public void showForm() throws IOException, ClassNotFoundException {
		Scanner read = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[1-4]{1}");
		int chooseOption;
		String readedChoose;
		
		do {
			System.out.println("What you want to do? Choose option.");
			showChoose();
			readedChoose = read.nextLine();
			Matcher matcher = choosePattern.matcher(readedChoose);
			if (matcher.matches())
				chooseOption = Integer.parseInt(readedChoose);
			else {
				System.out.println("Wrong choose");
				continue;
			}
			
			switch(chooseOption) {
				case 1:
					addOrder();
					break;
				case 2:
					showOrderState();
					break;
				case 3: 
					cancelOrder();
					break;
				case 4: 
					read.close();
					System.exit(0);
			}
		} while (true);
	}
	
	private void showChoose() {
		System.out.println("1. I want to repair my vehicle.");
		System.out.println("2. Show status of my notification");
		System.out.println("3. Cancel my notification");
		System.out.println("4. Exit");
	}
	
	private void addOrder() throws ClassNotFoundException, IOException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		OrderBuilder builder = new OrderBuilder();
		builder.createOrder();
		builder.buildClient();
		builder.buildCar();
		builder.buildNotification();
		connection.getOos().writeObject("insertorder");
		connection.getOos().writeObject(builder.getOrder());
		Order order = (Order)connection.getOis().readObject();
		System.out.println("Please remember id of the order, you need id to show status of repair. ID: " + order.getId());
	}
	
	private void showOrderState() throws ClassNotFoundException, IOException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Order order = new Order();
		System.out.println("Insert order ID: ");
		Integer id = reader.nextInt();
		connection.getOos().writeObject("findorder");
		connection.getOos().writeObject(id);
		order = (Order)connection.getOis().readObject();
		if (order.getId() == id) {
			System.out.println("Status of notification ID[" + order.getId() + "]: " + order.getStatus());
			System.out.println(order.getState().getDescription());
			order.getState().action(order);
			connection.getOos().writeObject("updateorder");
			connection.getOos().writeObject(order);
		} else {
			System.out.println("We don't have order with entered id");
		}
	}
	
	private void cancelOrder() throws ClassNotFoundException, IOException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Order order = new Order();
		System.out.println("Insert order ID: ");
		Integer id = reader.nextInt();
		connection.getOos().writeObject("findorder");
		connection.getOos().writeObject(id);
		order = (Order)connection.getOis().readObject();
		if (order.getId() == id && order.getState().checkCancelPossibility()) {
			System.out.println("Your order will be deleted");
			order.setStatus("closed");
			order.setState(new ClosedState());
			connection.getOos().writeObject("updateorder");
			connection.getOos().writeObject(order);
		} else {
			System.out.println("Your order is in progress, order won't be cancel or we don't have order with entered ID.");
		}
	}
}
