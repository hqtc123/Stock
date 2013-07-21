package com.hq.database;

import com.hq.config.GlobalField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午9:26
 * To change this template use File | Settings | File Templates.
 */
public class ConnManagement {
    private Connection conn;
    private String database;

    public ConnManagement(String database) {
        this.database = database;
    }

    public Connection getConn() {
        if (conn == null) {
            try {
                Class.forName(GlobalField.DRIVERCLASS);
                conn = DriverManager.getConnection(GlobalField.URL + database, GlobalField.USER, GlobalField.PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return conn;
    }

    public void closeConn() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
