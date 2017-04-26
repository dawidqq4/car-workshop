package carworkshop.server.listener;

import java.io.IOException;
import java.util.LinkedList;

import entities.Client;

public class ClientHandler extends Handler {

	@Override
	public void handle(Request request) throws ClassNotFoundException, IOException {
		switch (request.getCommand()) {
			case "insertclient":
				insertClientRequest(request);
				break;
			case "findclient":
				findClientRequest(request);
				break;
			case "selectclient":
				selectClientRequest(request);
				break;
			case "updateclient":
				updateClientRequest(request);
				break;
			case "deleteclient":
				deleteClientRequest(request);
				break;
			default:
				nextHandler(request);
				break;
		}
	}
	
	private void insertClientRequest(Request request) throws ClassNotFoundException, IOException {
		Client client = new Client();
		client = (Client) request.getObject();
		client = getJdbc().insertClient(client);
		getOos().writeObject(client);
	}
	
	private void findClientRequest(Request request) throws ClassNotFoundException, IOException {
		Client client = new Client();
		client.setId((Integer) request.getObject());
		client = getJdbc().findClient(client.getId());
	}
	
	private void selectClientRequest(Request request) throws ClassNotFoundException, IOException {
		Client client = new Client();
		LinkedList<Client> clients;
		client.setSurname((String) request.getObject());
		clients = getJdbc().selectClient(client.getSurname());
		for (Client i : clients) {
			getOos().writeObject(i);
		}
		getOos().writeObject(null);
	}
	
	private void updateClientRequest(Request request) throws ClassNotFoundException, IOException {
		Client client = new Client();
		client = (Client) request.getObject();
		getJdbc().updateClient(client);
	}
	
	private void deleteClientRequest(Request request) throws ClassNotFoundException, IOException {
		Client client = new Client();
		client = (Client) request.getObject();
		getJdbc().deleteClient(client.getId());
	}
}