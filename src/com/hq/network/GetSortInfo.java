package com.hq.network;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午10:49
 * use to get industry and other data
 */
public class GetSortInfo {
    public static final String SOURCE = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodes";
    String jsonString = "";

    public GetSortInfo() {
        init();
    }

    public void init() {
        try {
            URL url = new URL(SOURCE);
            URLConnection context = url.openConnection();
            InputStream in = context.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
            char[] cbuf = new char[1024];
            int cnum = -1;
            while ((cnum = br.read(cbuf)) != -1) {
                jsonString += new String(cbuf, 0, cnum);
            }

            System.out.println(jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void print() {
        JSONArray startObj = JSONArray.fromString(jsonString);
        JSONArray huS = JSONArray.fromObject(startObj.get(1));
        JSONArray hus2 = JSONArray.fromObject(huS.get(0));

        JSONArray fat = JSONArray.fromObject(hus2.get(1));

        JSONArray sectionArray = JSONArray.fromObject(fat.get(1));
        JSONArray sectionArray2 = JSONArray.fromObject(sectionArray.get(2));

        JSONArray regionArray = JSONArray.fromObject(fat.get(2));
        JSONArray regionArray2 = JSONArray.fromObject(regionArray.get(2));

        JSONArray industryArray = JSONArray.fromObject(fat.get(3));
        //for(industry*****)
        System.out.println(fat.get(1));
        System.out.println(fat.get(2));
        System.out.println(fat.get(3));
    }

    public void addRegion() {

    }

    public static void main(String[] args) {
        new GetSortInfo().print();
    }
}
