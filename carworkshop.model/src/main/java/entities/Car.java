package entities;

import java.io.Serializable;

public class Car implements Serializable{
	private static final long serialVersionUID = -4592663316659468389L;
	private int id;
	private String name;
	private String vin;
	private Client owner;
	
	public Car() {
	}
	
	public Car(Client owner) {
		this.owner = owner;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Client getOwner() {
		return owner;
	}
	
	public void setOwner(Client owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", name=" + name + ", vin=" + vin + ", owner=" + owner.getSurname() + "]";
	}
}
