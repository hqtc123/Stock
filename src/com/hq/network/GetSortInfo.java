package com.hq.network;

import com.hq.database.DataOperation;
import com.hq.model.Industry;
import com.hq.model.Region;
import com.hq.model.Section;
import net.sf.json.JSONArray;

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
    String database = "";
    DataOperation operation = null;

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
            database = "stock_sys";
            operation = new DataOperation(database);
//            System.out.println(jsonString);

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void deal() {
        JSONArray startObj = JSONArray.fromString(jsonString);
        JSONArray huS = JSONArray.fromObject(startObj.get(1));
        JSONArray hus2 = JSONArray.fromObject(huS.get(0));

        JSONArray fat = JSONArray.fromObject(hus2.get(1));

        JSONArray sectionArray = JSONArray.fromObject(fat.get(1));
        JSONArray sectionArray2 = JSONArray.fromObject(sectionArray.get(1));
//        System.out.println(sectionArray.get(2));
/**
 *add section
 */
//        for (int i = 0; i < sectionArray2.length(); i++) {
//            JSONArray array = JSONArray.fromObject(sectionArray2.get(i));
//            addSection(array.get(0).toString(), array.get(2).toString());
//        }

        JSONArray regionArray = JSONArray.fromObject(fat.get(2));
        JSONArray regionArray2 = JSONArray.fromObject(regionArray.get(1));
        /**
         * add region
         */
//        for(int i=0;i<regionArray2.length();i++){
//            JSONArray array=JSONArray.fromObject(regionArray2.get(i));
//            addRegion(array.get(0).toString(),array.get(2).toString());
//        }
        JSONArray industryArray = JSONArray.fromObject(fat.get(3));
        JSONArray industryArray2 = JSONArray.fromObject(industryArray.get(1));
        //for(industry*****)
//        System.out.println(industryArray2);
        for (int i = 0; i < industryArray2.length(); i++) {
            JSONArray array = JSONArray.fromObject(industryArray2.get(i));
            if (array.length() > 3) {
                JSONArray array2 = JSONArray.fromObject(array.get(1));
                for (int j = 0; j < array2.length(); j++) {
                    System.out.println(array2.get(j));
                    JSONArray array3 = JSONArray.fromObject(array2.get(j));
                    addIndustry(array3.get(0).toString(), array3.get(2).toString());
                }
            } else {
                addIndustry(array.get(0).toString(), array.get(2).toString());
            }
        }
    }

    public void addRegion(String regionName, String urlTag) {
        Region region = new Region(0, regionName, urlTag);

        operation.addRegion(region);
    }

    public void addSection(String sectionName, String urlTag) {
        Section section = new Section(0, sectionName, urlTag);

        operation.addSection(section);
    }

    public void addIndustry(String industryName, String urlTag) {
        Industry industry = new Industry(0, industryName, urlTag);

        operation.addIndustry(industry);
    }

//    public static void main(String[] args) {
//        GetSortInfo info = new GetSortInfo();
//        info.deal();
//    }
}
