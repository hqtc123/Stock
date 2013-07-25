package com.hq.network;

import com.hq.database.DataOperation;
import com.hq.model.Company;
import com.hq.model.Price;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午7:32
 * To change this template use File | Settings | File Templates.
 */
public class GetPrice {
    public static final String SOURCE = "http://hq.sinajs.cn/list=";
    DataOperation operation = null;
    // 股票名称
    public String stock_name;
    // 今日开盘价
    public double begin_price;
    // 昨日收盘价
    public double end_price;
    // 当前价格
    public double current_price;
    // 今日最高价
    public double today_max_price;
    // 今日最低价
    public double today_min_price;
    // 成交股票数量
    public long total_number;
    // 成交金额

    // 日期
    public Date time;

    public GetPrice() {
        operation=new DataOperation("stock_sys");
    }

    public static GetPrice getStockInfo(String stock_num) {
        try {
            String input = SOURCE + stock_num;
            String output = "";
            URL url = new URL(input);
            URLConnection context = url.openConnection();
            InputStream in = context.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
            char[] cbuf = new char[1024];
            int cnum = -1;
            while ((cnum = br.read(cbuf)) != -1) {
                output += new String(cbuf, 0, cnum);
            }
            output = output.substring(13 + stock_num.length());
            output = output.substring(0, output.indexOf("\""));
            String[] infos = output.split(",");

            GetPrice stockInfo = new GetPrice();
            stockInfo.stock_name = infos[0];
            stockInfo.begin_price = Double.valueOf(infos[1]);
            stockInfo.end_price = Double.valueOf(infos[2]);
            stockInfo.current_price = Double.valueOf(infos[3]);
            stockInfo.today_max_price = Double.valueOf(infos[4]);
            stockInfo.today_min_price = Double.valueOf(infos[5]);
            stockInfo.total_number = Long.valueOf(infos[8]);

            stockInfo.time = Date.valueOf(infos[30]);
            return stockInfo;
        } catch (Exception e) {
            System.out.println("异常信息：创建股票信息时发生异常\n" + e);
        }
        return null;
    }

    public void printStockInfo() {
        System.out.println(this.stock_name);

        System.out.println("\n当前价格     " + this.current_price + "\n");
        System.out.println("收盘"+this.end_price+"  "+"开盘"+this.begin_price);
        System.out.println("时间     " + this.time);
        System.out.println("\n成交量：" + this.total_number);

    }

    public void run() {
        ArrayList<Company> companies=operation.getCompanies();
        for(Company company:companies){
            GetPrice a= getStockInfo(company.getNumStr());
            if (a != null){
                Price price=new Price();
                price.setCompanyId(company.getId());
                price.setClosePrice(a.end_price);
                price.setOpenPrice(a.begin_price);
                price.setDate(a.time);
                operation.addPrice(price);
            }

        }

    }

    public static void main(String[] args){
        new GetPrice().run();
    }
}
