package entities;

import java.io.Serializable;

import entities.orderstates.*;

public class Order implements Serializable, Cloneable {
	private static final long serialVersionUID = 3221078292659529543L;
	private int id;
	private String description;
	private String date;
	private String last_access;
	private double price;
	private String status;
	private OrderState state;
	private Worker worker;
	private Car car;

	public Order() {
	}

	public Order(Car car, Worker worker) {
		this.car = new Car();
		this.car.setId(car.getId());
		this.car.setName(car.getName());
		this.car.setVin(car.getVin());
		this.car.setOwner(car.getOwner());
		this.worker = worker;
		state = new NewState();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLast_access() {
		return last_access;
	}

	public void setLast_access(String last_access) {
		this.last_access = last_access;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Object clone() {
		Object clone = null;

		try {
			clone = super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", description=" + description + ", date=" + date + ", status=" + status
				+ ", carOwner=" + car.getOwner().getSurname() + ", car name=" + car.getName() + "]\n";
	}
}
