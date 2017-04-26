package carworkshop.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Car;
import entities.Client;
import entities.Order;
import entities.Worker;
import entities.orderstates.ClosedState;
import entities.orderstates.FinishedState;
import entities.orderstates.InProgressState;
import entities.orderstates.NewState;
import entities.orderstates.WerificationState;

public class SQLiteJDBC implements FacadeJDBC{
	private Connection databaseConnection;
	private Statement statement;

	public SQLiteJDBC() {
	}

	
	@Override
	public void setConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:D:\\SQLite\\database.db");
			statement = databaseConnection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	    System.out.println("Opened database successfully");
	}
	
	
	@Override
	public Client insertClient(Client client) {
		PreparedStatement insertQuery;
        try {
             insertQuery = databaseConnection.prepareStatement("INSERT INTO CLIENT(NAME, SURNAME, ADDRESS, PHONE)"
             						    			  + " VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
             insertQuery.setString(1, client.getName());
             insertQuery.setString(2, client.getSurname());
             insertQuery.setString(3, client.getAddress());
             insertQuery.setInt(4, client.getPhone());
             insertQuery.executeUpdate();
             
             ResultSet rs = insertQuery.getGeneratedKeys();
             rs.next();
             client.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.err.println("Error at inserting into table client.");
        }
        return client;
	}
	
	
	@Override
    public Client findClient(int id) {
        Client client = new Client();
        ResultSet selectQuery;
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM CLIENT WHERE CLIENT.ID='" + id + "';");
            while(selectQuery.next()) {
                client.setId(selectQuery.getInt("ID"));
                client.setName(selectQuery.getString("NAME"));
                client.setSurname(selectQuery.getString("SURNAME"));
                client.setAddress(selectQuery.getString("ADDRESS"));
                client.setPhone(selectQuery.getInt("PHONE"));
            }
        } catch (SQLException e) {
        	System.out.println("Error to show data from table client");
            return null;
        }
        return client;
    }
    
	
	@Override
    public LinkedList<Client> selectClient(String srname) {
        LinkedList<Client> clients = new LinkedList<Client>();
        ResultSet selectQuery;
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM CLIENT WHERE CLIENT.SURNAME='" + srname + "';");
            while(selectQuery.next()) {
            	Client client = new Client();
                client.setId(selectQuery.getInt("ID"));
                client.setName(selectQuery.getString("NAME"));
                client.setSurname(selectQuery.getString("SURNAME"));
                client.setAddress(selectQuery.getString("ADDRESS"));
                client.setPhone(selectQuery.getInt("PHONE"));
             
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table client");
            return null;
        }
        return clients;
    } 
    

	@Override
    public void updateClient(Client client) {
		PreparedStatement editQuery;
        try {
        	editQuery = databaseConnection.prepareStatement("UPDATE CLIENT "
        													 + "SET name = ?, "
        													 + "surname = ?, "
        													 + "address = ?,"
        													 + "phone = ?"
        													 + "WHERE CLIENT.ID = " + client.getId() + ";");
        	editQuery.setString(1, client.getName());
        	editQuery.setString(2, client.getSurname());
        	editQuery.setString(3, client.getAddress());
        	editQuery.setInt(4, client.getPhone());
        	editQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to update data in table client");
        }
    }
    
	
	@Override
    public void deleteClient(int id) {
		PreparedStatement deleteQuery;
        try {
        	deleteQuery = databaseConnection.prepareStatement("DELETE FROM CLIENT "
        													 + "WHERE CLIENT.ID = " + id + ";");
        	deleteQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to delete data from table client");
        }
    }
    
	
	@Override
	public Worker insertWorker(Worker worker) {
		PreparedStatement insertQuery;
        try {
             insertQuery = databaseConnection.prepareStatement("INSERT INTO WORKER(NAME, SURNAME, ADDRESS, PHONE, LOGIN, PASSWORD)"
             						    			  + " VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
             insertQuery.setString(1, worker.getName());
             insertQuery.setString(2, worker.getSurname());
             insertQuery.setString(3, worker.getAddress());
             insertQuery.setInt(4, worker.getPhone());
             insertQuery.setString(5, worker.getLogin());
             insertQuery.setString(6, worker.getPassword());
             insertQuery.executeUpdate();
             
             ResultSet rs = insertQuery.getGeneratedKeys();
             rs.next();
             worker.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.err.println("Error at inserting into table worker");
            return null;
        }
        return worker;
	}
	
	
	@Override
    public Worker findWorker(int id) {
        ResultSet selectQuery;
    	Worker worker = new Worker();
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM WORKER WHERE WORKER.ID ='" + id + "';");
            while(selectQuery.next()) {
            	worker.setId(selectQuery.getInt("ID"));
            	worker.setName(selectQuery.getString("NAME"));
            	worker.setSurname(selectQuery.getString("SURNAME"));
            	worker.setAddress(selectQuery.getString("ADDRESS"));
            	worker.setPhone(selectQuery.getInt("PHONE"));
            	worker.setLogin(selectQuery.getString("LOGIN"));
            	worker.setPassword(selectQuery.getString("PASSWORD"));
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table worker");
            return null;
        }
        return worker;
    }
	
	
	@Override
	public LinkedList<Worker> selectWorker() {
        LinkedList<Worker> workers = new LinkedList<Worker>();
        ResultSet selectQuery;
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM public.WORKER;");
            while(selectQuery.next()) {
            	Worker worker = new Worker();
            	worker.setId(selectQuery.getInt("ID"));
            	worker.setName(selectQuery.getString("NAME"));
            	worker.setSurname(selectQuery.getString("SURNAME"));
            	worker.setAddress(selectQuery.getString("ADDRESS"));
            	worker.setPhone(selectQuery.getInt("PHONE"));
            	worker.setLogin(selectQuery.getString("LOGIN"));
            	worker.setPassword(selectQuery.getString("PASSWORD"));
            
            	workers.add(worker);
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table worker");
            return null;
        }
        return workers;
    }

	
	@Override
    public LinkedList<Worker> selectWorker(String login) {
        LinkedList<Worker> workers = new LinkedList<Worker>();
        ResultSet selectQuery;
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM WORKER WHERE WORKER.login = '" + login + "';");
            while(selectQuery.next()) {
            	Worker worker = new Worker();
            	worker.setId(selectQuery.getInt("ID"));
            	worker.setName(selectQuery.getString("NAME"));
            	worker.setSurname(selectQuery.getString("SURNAME"));
            	worker.setAddress(selectQuery.getString("ADDRESS"));
            	worker.setPhone(selectQuery.getInt("PHONE"));
            	worker.setLogin(selectQuery.getString("LOGIN"));
            	worker.setPassword(selectQuery.getString("PASSWORD"));
            
            	workers.add(worker);
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table worker");
            return null;
        }
        return workers;
    }
    
	
	@Override
    public Worker selectWorker(String login, String password) {
        Worker worker = new Worker();
        ResultSet selectQuery;
        try {
        	selectQuery = statement.executeQuery("SELECT * FROM public.WORKER WHERE WORKER.login = '" + login + "'"
        									   + "AND WORKER.password = '" + password + "';");
            selectQuery.next();
            worker.setId(selectQuery.getInt("ID"));
            worker.setName(selectQuery.getString("NAME"));
            worker.setSurname(selectQuery.getString("SURNAME"));
            worker.setAddress(selectQuery.getString("ADDRESS"));
            worker.setPhone(selectQuery.getInt("PHONE"));
            worker.setLogin(selectQuery.getString("LOGIN"));
            worker.setPassword(selectQuery.getString("PASSWORD"));
        } catch (SQLException e) {
            System.out.println("Error to show data from table worker");
            return null;
        }
        return worker;
    }
    
	
	@Override
    public void updateWorker(Worker worker) {
		PreparedStatement editQuery;
        try {
        	editQuery = databaseConnection.prepareStatement("UPDATE WORKER "
        													 + "SET name = ?, "
        													 + "surname = ?, "
        													 + "address = ?,"
        													 + "phone = ?,"
        													 + "password = ?"
        													 + "WHERE WORKER.ID = " + worker.getId() + ";");
        	editQuery.setString(1, worker.getName());
        	editQuery.setString(2, worker.getSurname());
        	editQuery.setString(3, worker.getAddress());
        	editQuery.setInt(4, worker.getPhone());
        	editQuery.setString(5, worker.getPassword());
        	editQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to update data in table worker");
        }
    }
    
	
	@Override
    public void deleteWorker(int id) {
		PreparedStatement deleteQuery;
        try {
        	deleteQuery = databaseConnection.prepareStatement("DELETE FROM WORKER "
        													 + "WHERE WORKER.ID = " + id + ";");
        	deleteQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to delete data from table worker");
        }
    }
	

	@Override
	public Car insertCar(Car car) {
		PreparedStatement insertQuery;
        try {
             insertQuery = databaseConnection.prepareStatement("INSERT INTO CAR(NAME, VIN, ID_CLIENT)"
             						    			  + " VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
             insertQuery.setString(1, car.getName());
             insertQuery.setString(2, car.getVin());
             insertQuery.setInt(3, car.getOwner().getId());
             insertQuery.executeUpdate();
             
             ResultSet rs = insertQuery.getGeneratedKeys();
             rs.next();
             car.setId(rs.getInt(1));
        } catch (SQLException e) {
            System.err.println("Error at inserting into table car");
            return null;
        }
        return car;
	}
    
	
	@Override
    public Car findCar(int id) throws SQLException {
        Car car = new Car();
        ResultSet selectQuery;
        Statement findStatement = databaseConnection.createStatement();
        try {
        	selectQuery = findStatement.executeQuery("SELECT * FROM CAR WHERE CAR.ID =" + id + ";");
            while(selectQuery.next()) {
            	car.setId(selectQuery.getInt("ID"));
            	car.setName(selectQuery.getString("NAME"));
            	car.setVin(selectQuery.getString("VIN"));
            	
            	int ownerId = selectQuery.getInt("ID_CLIENT");
            	Client client = new Client();
            	client = findClient(ownerId);
            	car.setOwner(client);
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table car");
            return null;
        }
        return car;
    }
    
   
	@Override
    public LinkedList<Car> selectCar(int userId) throws SQLException {
    	LinkedList<Car> cars = new LinkedList<Car>();
        ResultSet selectQuery;
        Statement selectStatement = databaseConnection.createStatement();
        try {
        	selectQuery = selectStatement.executeQuery("SELECT * FROM CAR WHERE CAR.ID_CLIENT =" + userId + ";");
            while(selectQuery.next()) {
                Car car = new Car();
            	car.setId(selectQuery.getInt("ID"));
            	car.setName(selectQuery.getString("NAME"));
            	car.setVin(selectQuery.getString("VIN"));
            	
            	int ownerId = selectQuery.getInt("ID_CLIENT");
            	Client client = findClient(ownerId);
            	car.setOwner(client);
            	
            	cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Error to show data from table car");
            return null;
        }
        return cars;
    }
    
    
	@Override
    public void updateCar(Car car) {
		PreparedStatement editQuery;
        try {
        	editQuery = databaseConnection.prepareStatement("UPDATE CAR "
        													 + "SET NAME = ?, "
        													 + "VIN = ?, "
        													 + "ID_CLIENT = ?"
        													 + "WHERE CAR.ID = " + car.getId() + ";");
        	editQuery.setString(1, car.getName());
        	editQuery.setString(2, car.getVin());
        	editQuery.setInt(3, car.getOwner().getId());
        	editQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to update data in table car");
        }
    }
    
    
	@Override
    public void deleteCar(int id) {
		PreparedStatement deleteQuery;
        try {
        	deleteQuery = databaseConnection.prepareStatement("DELETE FROM CAR "
        													 + "WHERE CAR.ID = " + id + ";");
        	deleteQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to delete data from table car");
        }
    }
	    
    
	@Override
	public Order insertOrder(Order order) {
		PreparedStatement insertQuery;
        try {
             insertQuery = databaseConnection.prepareStatement("INSERT INTO ORDER_TAB(DESCRIPTION, DATE, STATUS, ID_CAR)"
             						    			  + " VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
             insertQuery.setString(1, order.getDescription());
             insertQuery.setString(2, order.getDate());
             insertQuery.setString(3, order.getStatus());
             insertQuery.setInt(4, order.getCar().getId());
             insertQuery.executeUpdate();
             ResultSet rs = insertQuery.getGeneratedKeys();
             rs.next();
             order.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error at inserting into table order");
            return null;
        }
        return order;
	}
	
	
	@Override
    public Order findOrder(int id) throws SQLException {
    	Order order = new Order();
        ResultSet selectQuery;
        Statement selectStatement = databaseConnection.createStatement();
        try {
        	selectQuery = selectStatement.executeQuery("SELECT * FROM public.ORDER_TAB WHERE ORDER_TAB.ID = '" + id + "';");
            while(selectQuery.next()) {
            	order.setId(selectQuery.getInt("ID"));
            	order.setDescription(selectQuery.getString("DESCRIPTION"));
            	order.setDate(selectQuery.getString("DATE"));
            	order.setPrice(selectQuery.getDouble("PRICE"));
            	order.setStatus(selectQuery.getString("STATUS"));
            	int workerId = selectQuery.getInt("ID_WORKER");
            	int carId = selectQuery.getInt("ID_CAR");
            	
            	Worker worker = findWorker(workerId);
            	order.setWorker(worker);
            	
            	Car car = findCar(carId);
            	order.setCar(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return order;
    }

	
	@Override
    public LinkedList<Order> findOrder(Worker worker) throws SQLException {
    	LinkedList<Order> orders = new LinkedList<Order>();
        ResultSet selectQuery;
        Statement selectStatement = databaseConnection.createStatement();
        try {
        	selectQuery = selectStatement.executeQuery("SELECT * FROM public.ORDER_TAB WHERE ORDER_TAB.ID_WORKER = '" + worker.getId() + "';");
            while(selectQuery.next()) {
            	Order order = new Order();
            	order.setId(selectQuery.getInt("ID"));
            	order.setDescription(selectQuery.getString("DESCRIPTION"));
            	order.setDate(selectQuery.getString("DATE"));
            	order.setDate(selectQuery.getString("LAST_ACCESS"));
            	order.setPrice(selectQuery.getDouble("PRICE"));
            	order.setStatus(selectQuery.getString("STATUS"));
            	switch(order.getStatus()) {
            		case "new":
            			order.setState(new NewState());
            			break;
            		case "werification":
            			order.setState(new WerificationState());
            			break;
            		case "inprogress":
            			order.setState(new InProgressState());
            			break;
            		case "finished":
            			order.setState(new FinishedState());
            			break;
            		case "closed":
            			order.setState(new ClosedState());
            			break;
            	}
            	int workerId = selectQuery.getInt("ID_WORKER");
            	int carId = selectQuery.getInt("ID_CAR");
            	
            	Worker w = findWorker(workerId);
            	order.setWorker(w);
            	
            	Car car = findCar(carId);
            	order.setCar(car);
            	
            	orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return orders;
    }
    
	
	@Override
    public LinkedList<Order> selectOrder(String status) throws SQLException {
    	LinkedList<Order> orders = new LinkedList<Order>();
        ResultSet selectQuery;
        Statement selectStatement = databaseConnection.createStatement();
        try {
        	selectQuery = selectStatement.executeQuery("SELECT * FROM public.ORDER_TAB WHERE ORDER_TAB.STATUS = '" + status + "';");
            while(selectQuery.next()) {
            	Order order = new Order();
            	order.setId(selectQuery.getInt("ID"));
            	order.setDescription(selectQuery.getString("DESCRIPTION"));
            	order.setDate(selectQuery.getString("DATE"));
            	order.setPrice(selectQuery.getDouble("PRICE"));
            	order.setStatus(selectQuery.getString("STATUS"));
            	int workerId = selectQuery.getInt("ID_WORKER");
            	int carId = selectQuery.getInt("ID_CAR");
            	
            	Worker worker = findWorker(workerId);
            	order.setWorker(worker);
            	
            	Car car = findCar(carId);
            	order.setCar(car);
            	
            	orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return orders;
    }
        
	
	@Override
    public void updateOrder(Order order) {
		PreparedStatement editQuery;
        try {
        	editQuery = databaseConnection.prepareStatement("UPDATE ORDER_TAB "
        													 + "SET STATUS = ?, "
        													 + "PRICE = ? "
        													 + "ID_WORKER = ? "
        													 + "WHERE ORDER_TAB.ID = " + order.getId() + ";");
        	editQuery.setString(1, order.getStatus());
        	editQuery.setDouble(2, order.getPrice());
        	editQuery.setInt(3, order.getWorker().getId());
        	editQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to update data in table order");
        }
    }
    

	@Override
    public void deleteOrder(int id) {
		PreparedStatement deleteQuery;
        try {
        	deleteQuery = databaseConnection.prepareStatement("DELETE FROM ORDER_TAB "
        													 + "WHERE ORDER_TAB.ID = " + id + ";");
        	deleteQuery.execute();
        } catch (SQLException e) {
            System.out.println("Error to delete data from table order");
        }
    }
}
