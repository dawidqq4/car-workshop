package carworkshop.client.gui;

import java.io.IOException;
import java.util.Scanner;

import carworkshop.client.TcpConnectionClient;
import entities.Worker;

public class ProxyAccessPoint implements Access {
	private AccessPoint accessPoint;
	private Worker worker;
	
	@Override
	public void performOperations() throws IOException, ClassNotFoundException {
		Scanner read = new Scanner(System.in);
		worker = new Worker();
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		System.out.print("Login: ");
		worker.setLogin(read.nextLine());
		System.out.print("Password: ");
		worker.setPassword(read.nextLine());
		connection.getOos().writeObject("login");
		connection.getOos().writeObject(worker);
		worker = (Worker) connection.getOis().readObject();
		if (worker == null) {
			System.out.println("You don't have access to the system");
			System.exit(0);
		}
		accessPoint = new AccessPoint(worker);
		accessPoint.performOperations();
		read.close();
	}
}
