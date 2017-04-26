package carworkshop.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import carworkshop.server.dao.PostgreSqlJDBC;

public class MultithreadedServerTCP {
	private ExecutorService executorService;
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public MultithreadedServerTCP(int port, int numberOfThreads) {
		try {
			serverSocket = new ServerSocket(port);
			executorService = Executors.newFixedThreadPool(numberOfThreads);
		} catch (IOException e) {
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void start() {
		while (true) {
			try {
				socket = serverSocket.accept();
				Runnable connection = new Runnable() {
					@Override
					public void run() {
						createStreams();
						ServerThread thread = new ServerThread(new PostgreSqlJDBC());
						thread.setOos(oos);
						thread.setOis(ois);
						try {
							thread.listenClientAction();
						} catch (ClassNotFoundException | IOException | ParseException e ) {
						} 
					};
				};
				executorService.submit(connection);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createStreams() {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MultithreadedServerTCP server = new MultithreadedServerTCP(54321, 50);
		server.start();
	}
}