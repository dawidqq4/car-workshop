package entities;

import java.io.Serializable;

public class Worker implements Serializable {
	private static final long serialVersionUID = 7749124523799484448L;
	private int id;
	private String name;
	private String surname;
	private String address;
	private int phone;
	private String login;
	private String password;
	
	public Worker() {
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
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", surname=" + surname + ", address=" + address + ", phone="
				+ phone + ", login=" + login + "]\n";
	}
}
