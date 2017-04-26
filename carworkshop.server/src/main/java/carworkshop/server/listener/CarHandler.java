package carworkshop.server.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Car;
import entities.Client;


public class CarHandler extends Handler {

	@Override
	public void handle(Request request) throws ClassNotFoundException, IOException {
		switch (request.getCommand()) {
			case "insertcar":
				insertCarRequest(request);
				break;
			case "findcar":
				findCarRequest(request);
				break;
			case "selectcar":
				selectCarRequest(request);
				break;
			case "updatecar":
				updateCarRequest(request);
				break;
			case "deletecar":
				deleteCarRequest(request);
				break;
			default:
				nextHandler(request);
				break;
		}
	}
	
	private void insertCarRequest(Request request) throws ClassNotFoundException, IOException {
		Car car = new Car();
		car = (Car) request.getObject();
		car = getJdbc().insertCar(car);
		getOos().writeObject(car);
	}
	
	private void findCarRequest(Request request) throws ClassNotFoundException, IOException {
		Car car = new Car();
		car.setId((Integer) request.getObject());
		try {
			car = getJdbc().findCar(car.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void selectCarRequest(Request request) throws ClassNotFoundException, IOException {
		Car car = new Car();
		LinkedList<Car> cars = new LinkedList<Car>();
		car.setOwner((Client) request.getObject());
		try {
			cars = getJdbc().selectCar(car.getOwner().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Car i : cars) {
			getOos().writeObject(i);
		}
		getOos().writeObject(null);
	}
	
	private void updateCarRequest(Request request) throws ClassNotFoundException, IOException {
		Car car = new Car();
		car = (Car) request.getObject();
		getJdbc().updateCar(car);
	}
	
	private void deleteCarRequest(Request request) throws ClassNotFoundException, IOException {
		Car car = new Car();
		car = (Car) request.getObject();
		getJdbc().deleteCar(car.getId());
	}
}