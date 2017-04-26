package carworkshop.server.listener;

import java.io.IOException;
import java.util.LinkedList;

import entities.Worker;

public class WorkerHandler extends Handler {

	@Override
	public void handle(Request request) throws ClassNotFoundException, IOException {
		switch (request.getCommand()) {
			case "login":
				loginRequest(request);
				break;
			case "insertworker":
				insertWorkerRequest(request);
				break;
			case "findworker":
				findWorkerRequest(request);
				break;
			case "selectworker":
				selectWorkerRequest(request);
				break;
			case "deleteworker":
				deleteWorkerRequest(request);
				break;
			default:
				nextHandler(request);
				break;
		}
	}
	
	private void loginRequest(Request request) throws ClassNotFoundException, IOException {
		Worker worker = new Worker();
		worker = (Worker) request.getObject();
		worker = getJdbc().selectWorker(worker.getLogin(), worker.getPassword());
		getOos().writeObject(worker);
	}
	
	private void insertWorkerRequest(Request request) throws ClassNotFoundException, IOException {
		Worker worker = new Worker();
		worker = (Worker) request.getObject();
		worker = getJdbc().insertWorker(worker);
	}
	
	private void findWorkerRequest(Request request) throws ClassNotFoundException, IOException {
		Worker worker = new Worker();
		worker.setId((Integer) request.getObject());
		worker = getJdbc().findWorker(worker.getId());
		getOos().writeObject(worker);
	}
	
	private void selectWorkerRequest(Request request) throws ClassNotFoundException, IOException {
		LinkedList<Worker> workers;
		workers = getJdbc().selectWorker();
		for (Worker i : workers) {
			getOos().writeObject(i);
		}
		getOos().writeObject(null);
	}
	
	private void deleteWorkerRequest(Request request) throws ClassNotFoundException, IOException {
		Worker worker = new Worker();
		worker = (Worker) request.getObject();
		getJdbc().deleteWorker(worker.getId());
	}
}