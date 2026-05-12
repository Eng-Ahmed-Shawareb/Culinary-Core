package com.culinarycore.dao.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClsDatabaseConnection {

    private static ClsDatabaseConnection _instance = null;

    private Connection _connection;


    private String _URL = "jdbc:sqlserver://CulinaryCore.mssql.somee.com:1433;"
            + "databaseName=CulinaryCore;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private String _user = "MohamedAyser_SQLLogin_1";
    private String _password = "wyelu53fux";

    private ClsDatabaseConnection() {
        try {
            _connection = DriverManager.getConnection(_URL, _user, _password);
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }

    public static ClsDatabaseConnection getInstance() {
        if (_instance == null) {
            _instance = new ClsDatabaseConnection();
        }
        return _instance;
    }

    public Connection getConnection() {
        try {
            // Safety check: Re-establish the connection if it was closed
            if (_connection == null || _connection.isClosed()) {
                _connection = DriverManager.getConnection(_URL, _user, _password);
            }
        } catch (SQLException e) {
            System.out.println("Failed to check/re-establish connection: " + e.getMessage());
        }
        return _connection;
    }

    public void closeConnection() {
        try {
            if (_connection != null && !_connection.isClosed()) {
                _connection.close();
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }
}