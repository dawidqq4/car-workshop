package carworkshop.client;

import java.io.IOException;
import java.util.LinkedList;

import entities.Order;

public class OrderCache {
	private static LinkedList<Order> closedOrders = new LinkedList<Order>();

	public static Order getOrder(int orderId) {
		Order cachedOrder = searchOrder(orderId);
		return (Order) cachedOrder.clone();
	}

	public static void loadOrders() throws IOException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		connection.getOos().writeObject("selectorder");
		connection.getOos().writeObject("closed");
		while (true) {
			try {
				Order order = (Order) connection.getOis().readObject();
				if (order == null)
					break;
				else 
					if(order.getPrice() == 0)
						closedOrders.add(order);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(closedOrders);
	}
	
	private static Order searchOrder(int id) {
		for (Order i : closedOrders) {
			if (i.getId() == id)
				return i;
		}
		return null;
	}
}
