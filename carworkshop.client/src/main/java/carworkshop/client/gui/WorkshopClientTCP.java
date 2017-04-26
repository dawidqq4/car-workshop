package carworkshop.client.gui;

import java.io.IOException;

import carworkshop.client.TcpConnectionClient;


public class WorkshopClientTCP {
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		TcpConnectionClient serverConnection = TcpConnectionClient.getInstance();
		serverConnection.setHostIp("127.0.0.1");
		serverConnection.setPort(54321);
		serverConnection.createSocket();
		serverConnection.createStreams();
		Form view = new Form();
		view.setGUI();
	}
}
