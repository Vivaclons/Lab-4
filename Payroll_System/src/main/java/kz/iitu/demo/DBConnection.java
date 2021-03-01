package kz.iitu.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;

@Component("dbc")
public class DBConnection {
    @Value("${application.URL}")
    private String url;
    @Value("${application.USERNAME}")
    private String username;
    @Value("${application.PASSWORD}")
    private String password;
    private Connection connect;

    public DBConnection() {}

    public DBConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void openConnect() throws Exception {
        connect = DriverManager.getConnection(url, username, password);
    }

    public void closeConnect() throws Exception {
        connect.close();
    }

    @PostConstruct
    public void init() {
        System.out.println("Set Init DBConnection\n");
        try {
            openConnect();
        } catch (Exception ex) {
            System.out.println("Connection failed!");
            System.out.println(ex);
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("\nWas destroy DBConnection");
        try {
            closeConnect();
        } catch (Exception ex) {
            System.out.println("Closing failed!");
            System.out.println(ex);
        }
    }

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException sqlEx) {
            System.out.println("SQL query failed!");
            System.out.println(sqlEx);
        }
        return resultSet;
    }

    public void updateData(String sql) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            System.out.println("Date update failed!");
            System.out.println(sqlEx);
        }
    }
}
