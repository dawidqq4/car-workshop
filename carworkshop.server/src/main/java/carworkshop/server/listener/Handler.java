package carworkshop.server.listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import carworkshop.server.dao.FacadeJDBC;

public abstract class Handler {
    private Handler succesor;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    protected FacadeJDBC jdbc;
 
    
	public ObjectOutputStream getOos() {
		return oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}
    
	public FacadeJDBC getJdbc() {
		return jdbc;
	}

	public void setSuccesor(Handler succesor) {
        this.succesor = succesor;
    }
 
    public Handler getSuccesor() {
        return succesor;
    }
 
    public void createStreams(FacadeJDBC jdbc, ObjectOutputStream oos, ObjectInputStream ois) {
    	this.jdbc = jdbc;
    	this.oos = oos;
    	this.ois = ois;
    }
    
    protected void nextHandler(Request request) throws ClassNotFoundException, IOException{
        if(this.succesor != null){
            this.getSuccesor().handle(request);
        }
    }
 
    abstract public void handle(Request request) throws ClassNotFoundException, IOException;
}