package carworkshop.server.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Order;
import entities.Worker;

public class OrderHandler extends Handler {

	@Override
	public void handle(Request request) throws ClassNotFoundException, IOException {
		switch (request.getCommand()) {
			case "insertorder":
				insertOrderRequest(request);
				break;
			case "findorder":
				findOrderRequest(request);
				break;
			case "findworkerorders":
				findWorkerOrdersRequest(request);
				break;
			case "selectorder":
				selectOrderRequest(request);
				break;
			case "updateorder":
				updateOrderRequest(request);
				break;
			case "deleteorder":
				deleteOrderRequest(request);
				break;
			default:
				nextHandler(request);
				break;
		}
	}
	
	private void insertOrderRequest(Request request) throws ClassNotFoundException, IOException {
		Order order = new Order();
		order = (Order) request.getObject();
		order = getJdbc().insertOrder(order);
		getOos().writeObject(order);
	}
	
	private void findOrderRequest(Request request) throws ClassNotFoundException, IOException {
		Order order = new Order();
		int idOrder = (Integer) request.getObject();
		try {
			order = getJdbc().findOrder(idOrder);
			getOos().writeObject(order);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void findWorkerOrdersRequest(Request request) throws ClassNotFoundException, IOException {
		LinkedList<Order> orders = new LinkedList<Order>();
		Worker worker = (Worker) request.getObject();
		try {
			orders = getJdbc().findOrder(worker);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Order i : orders) {
			getOos().writeObject(i);
		}
		getOos().writeObject(null);
	}
	
	private void selectOrderRequest(Request request) throws ClassNotFoundException, IOException {
		Order order = new Order();
		LinkedList<Order> orders = new LinkedList<Order>();
		order.setStatus((String) request.getObject());
		try {
			orders = getJdbc().selectOrder(order.getStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Order i : orders) {
			getOos().writeObject(i);
		}
		getOos().writeObject(null);
	}
	
	private void updateOrderRequest(Request request) throws ClassNotFoundException, IOException {
		Order order = new Order();
		order = (Order) request.getObject();
		getJdbc().updateOrder(order);
	}
	
	private void deleteOrderRequest(Request request) throws ClassNotFoundException, IOException {
		Order order = new Order();
		order = (Order) request.getObject();
		getJdbc().deleteOrder(order.getId());
	}
}