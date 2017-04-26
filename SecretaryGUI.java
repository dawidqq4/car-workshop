package carworkshop.client.gui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import carworkshop.client.OrderCache;
import carworkshop.client.TcpConnectionClient;
import entities.Order;
import entities.Worker;


public class SecretaryGUI extends GUI {
	
	public SecretaryGUI() {
	}
	
	
	public void showForm() throws IOException, ClassNotFoundException {
		Scanner read = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[1-6]{1}");	
		String readedChoose;
		int chooseOption;
		
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
					addWorker();
					break;
				case 2:
					showWorker();
					break;
				case 3:
					giveOrderForWorker();
					break;
				case 4:
					changeStateOfOrder();
					break;
				case 5:
					read.close();
					System.exit(0);
			}
		} while (true);
	}
	
	private void showChoose() {
		System.out.println("1. Add worker");
		System.out.println("2. Show workers");
		System.out.println("3. Give order for worker");
		System.out.println("4. Change state of closed order");
		System.out.println("5. Exit");
	}
	
	private void addWorker() throws IOException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		Worker worker = new Worker();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.print("Name: ");
		worker.setName(reader.nextLine());
		System.out.print("Surname: ");
		worker.setSurname(reader.nextLine());
		System.out.print("Address: ");
		worker.setAddress(reader.nextLine());
		System.out.print("Phone: ");
		worker.setPhone(Integer.parseInt(reader.nextLine()));
		System.out.print("Login: ");
		worker.setLogin(reader.nextLine());
		System.out.print("Password: ");
		worker.setPassword(reader.nextLine());
		connection.getOos().writeObject("insertworker");
		connection.getOos().writeObject(worker);
	}
	
	private void showWorker() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		LinkedList<Worker> workers = new LinkedList<Worker>();
		connection.getOos().writeObject("selectworker");
		connection.getOos().writeObject(null);
		while (true) {
			try {
				Worker worker = (Worker) connection.getOis().readObject();
				if (worker == null)
					break;
				else
					workers.add(worker);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(workers);
	}
	
	
	private void giveOrderForWorker() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		LinkedList<Order> orders = new LinkedList<Order>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		connection.getOos().writeObject("selectorder");
		connection.getOos().writeObject("new");
		while (true) {
			try {
				Order order = (Order) connection.getOis().readObject();
				if (order == null)
					break;
				else
					orders.add(order);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(orders);
		if (orders.isEmpty()) {
			return;
		}
		
		System.out.println("Enter ID order: ");
		Integer id = reader.nextInt();
		boolean condition = false;
		for (Order i : orders) {
			if (i.getId() == id)
				condition = true;
		}
		if (condition == true) {
			connection.getOos().writeObject("findorder");
			connection.getOos().writeObject(id);
			Order order = (Order) connection.getOis().readObject();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime now = LocalDateTime.now();
			order.setLast_access(dtf.format(now));
			showWorker();
			System.out.println("Enter ID worker: ");
			Integer workerId = reader.nextInt();
			connection.getOos().writeObject("findworker");
			connection.getOos().writeObject(workerId);
			Worker worker = (Worker) connection.getOis().readObject();
			if (worker != null) {
				order.setWorker(worker);
				order.setStatus("werification");
				connection.getOos().writeObject("updateorder");
				connection.getOos().writeObject(order);
			} else
				System.out.println("Error employee ID.");
		} else
			System.out.println("Error order ID.");
	}
	
	private void changeStateOfOrder() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		OrderCache.loadOrders();
		System.out.println("Enter ID order: ");
		Integer id = reader.nextInt();
		connection.getOos().writeObject("findorder");
		connection.getOos().writeObject(id);
		Order order = (Order) connection.getOis().readObject();
		order.setStatus("new");
		connection.getOos().writeObject("updateorder");
		connection.getOos().writeObject(order);
	}
}
