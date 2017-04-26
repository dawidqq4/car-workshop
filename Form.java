package carworkshop.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Form {
	private GUI gui;
	
	public Form() {
	}

	
	public void setGUI() throws IOException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("If you want to go to the client view press enter, else press another key.");
		int ascii = br.read();
		if (ascii == 13) {
			gui = new ClientGUI();
			gui.showForm();
		} else {
			showLoginForm();
		}
	}
	
	
	public void showLoginForm() throws IOException, ClassNotFoundException {
		Access access = new ProxyAccessPoint();
		access.performOperations();
	}
}

