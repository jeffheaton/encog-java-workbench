package org.encog.workbench.config;

import org.encog.neural.persist.EncogPersistedObject;
import org.encog.neural.persist.Persistor;

public class EncogWorkBenchConfig implements EncogPersistedObject {
	private String databaseConnectionString = "";
	private String databaseDriver = "";
	private String databaseUserID = "";
	private String databasePassword = "";
	public String getDatabaseConnectionString() {
		return databaseConnectionString;
	}
	public void setDatabaseConnectionString(String databaseConnectionString) {
		this.databaseConnectionString = databaseConnectionString;
	}
	public String getDatabaseDriver() {
		return databaseDriver;
	}
	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}
	public String getDatabaseUserID() {
		return databaseUserID;
	}
	public void setDatabaseUserID(String databaseUserID) {
		this.databaseUserID = databaseUserID;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public Persistor createPersistor() {
		return null;
	}

	public String getDescription() {
		return null;
	}

	public String getName() {
		return null;
	}

	public void setDescription(String theDescription) {
	}
	
	public void setName(String theName) {
	}
	
	
	
}
