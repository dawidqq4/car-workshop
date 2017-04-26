package carworkshop.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import carworkshop.server.dao.*;
import carworkshop.server.listener.*;


public class ServerThread extends DatabaseAbstraction {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private FacadeJDBC database;
	
	public ServerThread(FacadeJDBC jdbc) {
		super(jdbc);
		database = getJDBCImplementation();
		database.setConnection();
	}
	

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public void listenClientAction() throws ClassNotFoundException, IOException, ParseException {
		Object object;
		String action;
		Handler chainClient = new ClientHandler();
		Handler chainWorker = new WorkerHandler();
		Handler chainCar = new CarHandler();
		Handler chainOrder = new OrderHandler();
		
		chainClient.setSuccesor(chainWorker);
		chainClient.createStreams(database, oos, ois);
		
		chainWorker.setSuccesor(chainCar);
		chainWorker.createStreams(database, oos, ois);
		
		chainCar.setSuccesor(chainOrder);
		chainCar.createStreams(database, oos, ois);
		
		chainOrder.createStreams(database, oos, ois);
		
		while(true) {
			action = (String) ois.readObject();
			object = ois.readObject();
			Request request = new Request(action, object);
			chainClient.handle(request);
		}
	}
}

