package com.dsam.assignment02.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class CloudSqlDataSource {

	private static final String DB_NAME = "prod-db";
	private static final String DB_USER = "postgres";
	private static final String DB_PASS = "64Jk3j1fdkGuJN4d";
	private static final String DB_HOST = "35.242.192.31";
	private static final String DB_PORT = "5432";
	private final HikariDataSource datasource;

	public CloudSqlDataSource() {
		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME));
		config.setUsername(DB_USER);
		config.setPassword(DB_PASS);
		datasource = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}
}
