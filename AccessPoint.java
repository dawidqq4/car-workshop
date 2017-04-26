package carworkshop.client.gui;

import java.io.IOException;

import entities.Worker;

public class AccessPoint implements Access {
	private GUI gui;
	private Worker worker;
	
	public AccessPoint(Worker worker) {
		this.worker = worker;
	}
	
	@Override
	public void performOperations() throws IOException, ClassNotFoundException {
		if (worker.getId() == 0) {
			gui = new SecretaryGUI();
		}
		else {
			gui = new WorkerGUI(worker);
		}
		gui.showForm();
	}
}
