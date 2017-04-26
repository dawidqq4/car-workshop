package carworkshop.server;

import carworkshop.server.dao.FacadeJDBC;

public abstract class DatabaseAbstraction {
	private FacadeJDBC jdbc;
	
	public DatabaseAbstraction(FacadeJDBC jdbc) {
		this.jdbc = jdbc;
	}
	
	protected FacadeJDBC getJDBCImplementation() {
		return jdbc;
	}
}
