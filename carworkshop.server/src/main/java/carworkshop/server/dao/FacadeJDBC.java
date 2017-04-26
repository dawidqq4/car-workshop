package carworkshop.server.dao;

import java.sql.SQLException;
import java.util.LinkedList;

import entities.Car;
import entities.Client;
import entities.Order;
import entities.Worker;

public interface FacadeJDBC {
	public void setConnection();
	
	public Client insertClient(Client client);
	public Worker insertWorker(Worker worker);
	public Car insertCar(Car car);
	public Order insertOrder(Order order);
	
	public Client findClient(int id);
	public Worker findWorker(int id);
	public Car findCar(int id) throws SQLException;
	public Order findOrder(int id) throws SQLException;
	public LinkedList<Order> findOrder(Worker worker) throws SQLException;
	
	public LinkedList<Client> selectClient(String srname);
	public LinkedList<Worker> selectWorker();
	public LinkedList<Worker> selectWorker(String login);
	public Worker selectWorker(String login, String password);
	public LinkedList<Car> selectCar(int userId) throws SQLException;
	public LinkedList<Order> selectOrder(String status) throws SQLException;
	
	public void updateClient(Client client);
	public void updateWorker(Worker worker);
	public void updateCar(Car car);
	public void updateOrder(Order order);
	
	public void deleteClient(int id);
	public void deleteWorker(int id);
	public void deleteCar(int id);
	public void deleteOrder(int id);
}