/*
 * Encog(tm) Workbench v2.3
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.workbench.config;

import org.encog.EncogError;
import org.encog.persist.EncogPersistedObject;
import org.encog.persist.Persistor;

public class EncogWorkBenchConfig implements EncogPersistedObject {
	
	private String databaseConnectionString = "";
	private String databaseDriver = "";
	private String databaseUserID = "";
	private String databasePassword = "";
	private String databaseDialect = "";
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
		return "config";
	}

	public void setDescription(String theDescription) {
	}
	
	public void setName(String theName) {
	}
	public String getDatabaseDialect() {
		return databaseDialect;
	}
	public void setDatabaseDialect(String databaseDialect) {
		this.databaseDialect = databaseDialect;
	}
	
	public Object clone()
	{
		throw new EncogError("Clone not supported");
	}
	
	
}
