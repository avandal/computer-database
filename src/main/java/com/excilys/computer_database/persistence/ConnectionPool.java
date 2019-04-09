package com.excilys.computer_database.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

	private HikariConfig config;
	private static volatile ConnectionPool instance = null;
	private static HikariDataSource datasource;

	private ConnectionPool(String dataSource) {
		this.config = new HikariConfig(dataSource);
		ConnectionPool.datasource = new HikariDataSource(config);
	}

	public static ConnectionPool getInstance(String datasource)
	{   
		if (instance == null) {
			synchronized(ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool(datasource);
				}
			}
		}
		return instance;
	}

	public HikariDataSource getDataSource() {
		return datasource;
	}
}
