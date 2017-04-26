package carworkshop.server.listener;
 
public class Request {
    private String command;
    private Object object;
 
    public Request(String command, Object object) {
        super();
        this.command = command;
        this.object = object;
    }
 
    public String getCommand() {
        return command;
    }
 
    public Object getObject() {
        return object;
    }
 
}