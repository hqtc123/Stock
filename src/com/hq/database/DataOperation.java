package com.hq.database;

import com.hq.model.Company;
import com.hq.model.Industry;
import com.hq.model.Region;
import com.hq.model.Section;
import com.hq.model.Price;

import java.sql.*;
import java.util.ArrayList;

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
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();
            preState = conn.prepareStatement("SELECT * FROM industry WHERE industry_name=? AND url_tag=?");
            preState.setString(1, industry.getName());
            preState.setString(2, industry.getUrlTag());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("already exist a record in database");
                return;
            }

            conn = connManagement.getConn();
            preState = conn.prepareStatement("INSERT INTO industry(industry_name,url_tag) VALUES (?,?)");
            preState.setString(1, industry.getName());
            preState.setString(2, industry.getUrlTag());
            preState.executeUpdate();

            System.out.println("insert industry " + industry.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {

        }
    }

    public void addSection(Section section) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM sections WHERE section_name=? AND url_tag=?");
            preState.setString(1, section.getName());
            preState.setString(2, section.getUrlTag());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("already exist a record in database");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO sections(section_name,url_tag) VALUES (?,?)");
            preState.setString(1, section.getName());
            preState.setString(2, section.getUrlTag());
            preState.executeUpdate();

            System.out.println("insert section " + section.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void addRegion(Region region) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM region WHERE region_name=? AND url_tag=?");
            preState.setString(1, region.getName());
            preState.setString(2, region.getUrlTag());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("already exist a record in database");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO region(region_name,url_tag) VALUES (?,?)");
            preState.setString(1, region.getName());
            preState.setString(2, region.getUrlTag());
            preState.executeUpdate();

            System.out.println("insert industry " + region.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ArrayList<Region> getRegions() {
        ArrayList<Region> list = new ArrayList<Region>();
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM region");

            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                Region region = null;
                int id = rs.getInt("region_id");
                String name = rs.getString("region_name");
                String urlTag = rs.getString("url_tag");
                region = new Region(id, name, urlTag);
                list.add(region);
                count++;
            }
            if (count == 0) {
                System.out.println("no region");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public void addRegionCompany(Company company) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM company WHERE company_no=?");
            preState.setString(1, company.getNumStr());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("already exist a record in database");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO company(company_name,company_no," +
                    "region_id)VALUES (?,?,?)");
            preState.setString(1, company.getName());
            preState.setString(2, company.getNumStr());
            preState.setInt(3, company.getRegionId());
            preState.executeUpdate();

            System.out.println("insert company " + company.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ArrayList<Industry> getIndustrys() {
        ArrayList<Industry> list = new ArrayList<Industry>();
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM industry");

            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                Industry industry = null;
                int id = rs.getInt("industry_id");
                String name = rs.getString("industry_name");
                String urlTag = rs.getString("url_tag");
                industry = new Industry(id, name, urlTag);
                list.add(industry);
                count++;
            }
            if (count == 0) {
                System.out.println("no industry");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public void addIndustryCompany(Company company) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM company WHERE company_no=?");
            preState.setString(1, company.getNumStr());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                preState = conn.prepareStatement("UPDATE company SET industry_id=? WHERE company_no=?");
                preState.setInt(1, company.getIndustryId());
                preState.setString(2, company.getNumStr());
                preState.executeUpdate();
                System.out.println("industry update");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO company(company_name,company_no," +
                    "industry_id)VALUES (?,?,?)");
            preState.setString(1, company.getName());
            preState.setString(2, company.getNumStr());
            preState.setInt(3, company.getIndustryId());
            preState.executeUpdate();

            System.out.println("insert company industry" + company.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ArrayList<Section> getSections() {
        ArrayList<Section> list = new ArrayList<Section>();
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM sections");

            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                Section section = null;
                int id = rs.getInt("section_id");
                String name = rs.getString("section_name");
                String urlTag = rs.getString("url_tag");
                section = new Section(id, name, urlTag);
                list.add(section);
                count++;
            }
            if (count == 0) {
                System.out.println("no section");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public void addSectionCompany(Company company) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM company WHERE company_no=?");
            preState.setString(1, company.getNumStr());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                preState = conn.prepareStatement("UPDATE company SET section_id=? WHERE company_no=?");
                preState.setInt(1, company.getSectionId());
                preState.setString(2, company.getNumStr());
                preState.executeUpdate();
                System.out.println("section update  ====  ");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO company(company_name,company_no," +
                    "section_id)VALUES (?,?,?)");
            preState.setString(1, company.getName());
            preState.setString(2, company.getNumStr());
            preState.setInt(3, company.getSectionId());
            preState.executeUpdate();

            System.out.println("insert company section" + company.getName());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void addPrice(Price price) {
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM price WHERE company_id=? AND DATE =?");
            preState.setInt(1, price.getCompanyId());
            preState.setDate(2, price.getDate());
            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("already exist a record in database");
                return;
            }

            preState = conn.prepareStatement("INSERT INTO price(company_id,close_price," +
                    "open_price,DATE)VALUES (?,?,?,?)");
            preState.setInt(1, price.getCompanyId());
            preState.setDouble(2, price.getClosePrice());
            preState.setDouble(3, price.getOpenPrice());
            preState.setDate(4, price.getDate());
            preState.executeUpdate();

            System.out.println("insert pirce " + price.getCompanyId() + price.getDate());

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ArrayList<Company> getCompanies() {
        ArrayList<Company> list = new ArrayList<Company>();
        Connection conn = null;
        PreparedStatement preState;
        try {
            conn = connManagement.getConn();

            preState = conn.prepareStatement("SELECT * FROM company");

            ResultSet rs = preState.executeQuery();
            int count = 0;
            while (rs.next()) {
                Company company = null;
                int id = rs.getInt("company_id");
                String numStr = rs.getString("company_no");
                company = new Company();
                company.setId(id);
                company.setNumStr(numStr);
                list.add(company);
                count++;
            }
            if (count == 0) {
                System.out.println("no section");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public void addCompanySix(int companyId) {
        Connection conn = null;
        PreparedStatement preStatement = null;
        try {
            conn = connManagement.getConn();
            preStatement = conn.prepareStatement("SELECT * FROM company_six WHERE company_id=?");
            preStatement.setInt(1, companyId);
            ResultSet rs = preStatement.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count != 0) {
                System.out.println("Already a data with same company_id in database");
                return;
            }

            preStatement = conn.prepareStatement("INSERT INTO company_six(company_id) VALUES (?)");
            preStatement.setInt(1, companyId);
            preStatement.executeUpdate();
            System.out.println("data inserted");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void updateStockAandAll(String companyNo, double stockAll, double stockA) {
        Connection conn = null;
        PreparedStatement preStatement = null;
        try {
            conn = connManagement.getConn();
            preStatement = conn.prepareStatement("SELECT company_id FROM company WHERE company_no=?");
            preStatement.setString(1, companyNo);
            ResultSet rs = preStatement.executeQuery();
            int count = 0;
            int companyId = 0;
            while (rs.next()) {
                companyId = rs.getInt("company_id");
                count++;
            }
            if (count == 0) {
                System.out.println("no such company in database , please check carefully");
                return;
            }
            preStatement = conn.prepareStatement("UPDATE company_six SET stock_all=?,stock_A=? WHERE " +
                    "company_id=?");
            preStatement.setDouble(1, stockAll);
            preStatement.setDouble(2, stockA);
            preStatement.setInt(3, companyId);
            preStatement.executeUpdate();
            System.out.println("update  succcess");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void updateDebt(int companyId, double currentDebt, double longDebt, double bossProfit) {
        Connection conn = null;
        PreparedStatement preStatement = null;
        try {
            conn = connManagement.getConn();
            preStatement = conn.prepareStatement("UPDATE company_six SET current_debt=?,long_debt=?,boss_profit=? WHERE " +
                    "company_id=?");
            preStatement.setDouble(1, currentDebt);
            preStatement.setDouble(2, longDebt);
            preStatement.setDouble(3, bossProfit);
            preStatement.setInt(4, companyId);
            preStatement.executeUpdate();
            System.out.println("update  succcess");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void updatePerValue(int companyId, double perValue) {
        Connection conn = null;
        PreparedStatement preStatement = null;
        try {
            conn = connManagement.getConn();
            preStatement = conn.prepareStatement("UPDATE company_six SET per_value=? WHERE " +
                    "company_id=?");
            preStatement.setDouble(1, perValue);
            preStatement.setInt(2, companyId);
            preStatement.executeUpdate();
            System.out.println("update  succcess");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
