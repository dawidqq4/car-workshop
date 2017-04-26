package carworkshop.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpConnectionClient {
	private static TcpConnectionClient instance = null;
	private int port;
	private String hostIp;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private TcpConnectionClient() { 
	}
	
	
	public static TcpConnectionClient getInstance() {		
		if (instance == null) {
			instance = new TcpConnectionClient();
			return instance;
		}
		return instance;
	}

	
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public void setPort(int port) {
		this.port = port;
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
	
	public void createStreams() {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createSocket() {
		try {
			Socket socket = new Socket(hostIp, port);
			this.socket = socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
