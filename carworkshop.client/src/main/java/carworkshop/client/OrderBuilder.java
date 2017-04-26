package carworkshop.client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import entities.Car;
import entities.Client;
import entities.Order;

public class OrderBuilder {
	private Car car;
	private Client client;
	private Order order;

	
	public void createOrder() {
		client = new Client();
		car = new Car();
		order = new Order();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		order.setDate(dtf.format(now));
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void buildClient() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);
		System.out.print("Enter name: ");
		client.setName(read.nextLine());
		System.out.print("Enter surname: ");
		client.setSurname(read.nextLine());
		System.out.print("Enter address: ");
		client.setAddress(read.nextLine());
		System.out.print("Phone: ");
		client.setPhone(Integer.parseInt(read.nextLine()));
		connection.getOos().writeObject("insertclient");
		connection.getOos().writeObject(client);
		client = (Client) connection.getOis().readObject();
		System.out.println(client);
	}
	
	public void buildCar() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);
		System.out.print("Car name: ");
		car.setName(read.nextLine());
		Pattern choosePattern = Pattern.compile("([A-Z]{3})([A-Z1-9]{6})([1-9]{8})");
		String readedVin;
		System.out.println("Enter vehicle vin: ");
		do {
			readedVin = read.nextLine();
			Matcher matcher = choosePattern.matcher(readedVin);
			if (matcher.matches()) {
				car.setVin(readedVin);
				break;
			} else {
				System.out.println("Wrong vin number");
				continue;
			}
		} while(true);
		car.setOwner(client);
		connection.getOos().writeObject("insertcar");
		connection.getOos().writeObject(car);
		car = (Car) connection.getOis().readObject();
		System.out.println(car);
	}
	
	public void buildNotification() {
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);
		String orderData;
		System.out.print("What are problems have you with car?: ");
		orderData = read.nextLine();
		order.setDescription(orderData);
		order.setStatus("new");
		order.setCar(car);
	}
}

