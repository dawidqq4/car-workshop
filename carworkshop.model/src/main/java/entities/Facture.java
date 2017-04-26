package entities;


public class Facture {
	private double price;
	private String date;
	private String description;
	private Client owner;
	
	public Facture(String date) {
		this.date = date;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getOwner() {
		return owner;
	}
	
	public void setOwner(Client owner) {
		this.owner = owner;
	}


	@Override
	public String toString() {
		return "Facture [price=" + String.format("%.2f", price) + ", date=" + date + ", description=" + description + ", owner=" + owner.getName() + " " + owner.getSurname() + "]";
	}
}
