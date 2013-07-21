package com.hq.database;

import com.hq.model.Industry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午10:14
 * To change this template use File | Settings | File Templates.
 */
public class DataOperation {
    private ConnManagement connManagement;

    public DataOperation(String database) {
        this.connManagement = new ConnManagement(database);
    }

    public void addIndustry(Industry industry) {
        Connection conn;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();
            preState = conn.prepareStatement("INSERT INTO industry(industry_name,url_tag) VALUES (?,?)");
            preState.setString(1, industry.getName());
            preState.setString(2, industry.getUrlTag());
            preState.executeUpdate();

            System.out.println("insert industry "+industry.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
