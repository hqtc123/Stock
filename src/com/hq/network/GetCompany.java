package com.hq.network;

import com.hq.database.DataOperation;
import com.hq.model.Company;
import com.hq.model.Industry;
import com.hq.model.Region;
import com.hq.model.Section;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-25
 * Time: 上午1:44
 * To change this template use File | Settings | File Templates.
 */
public class GetCompany {
    final String BASE1 = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=";
    final String BASE2 =
            "&num=80&sort=symbol&asc=1&node=";
    final String BASE3 = "&_s_r_a=setlen";
    ArrayList<Company> companies = new ArrayList<Company>();
    DataOperation operation = null;

    public GetCompany() {
        operation = new DataOperation("stock_sys");
    }

    public void setRegionCompany() throws IOException {
        ArrayList<Region> regions = operation.getRegions();
        for (Region region : regions) {
            String html = "";
            int page = 1;
            String urlTag = region.getUrlTag();
            while (!html.contains("null")) {
                html = "";
                String urlStr = BASE1 + page + BASE2 + region.getUrlTag() + BASE3;
                URL url = new URL(urlStr);
                URLConnection context = url.openConnection();
                InputStream in = context.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
                char[] cbuf = new char[1024];
                int cnum = -1;
                while ((cnum = br.read(cbuf)) != -1) {
                    html += new String(cbuf, 0, cnum);
                }
                if (html.contains("null")) {
                    break;
                }
                JSONArray array = JSONArray.fromObject(html);
                System.out.println(array);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonCompany = JSONObject.fromObject(array.get(i));
                    System.out.println(jsonCompany.get("symbol") + " " + jsonCompany.get("name"));
                    String numStr=jsonCompany.get("symbol").toString();
                    String name=jsonCompany.get("name").toString();
                    Company company=new Company();
                    company.setNumStr(numStr);
                    company.setName(name);
                    company.setRegionId(region.getId());
                    operation.addRegionCompany(company);
                }
                page++;
            }
        }
    }

    public void setIndustryCompany() throws IOException {
        ArrayList<Industry> industries = operation.getIndustrys();
        for (Industry industry:industries) {
            String html = "";
            int page = 1;
            String urlTag = industry.getUrlTag();
            while (!html.contains("null")) {
                html = "";
                String urlStr = BASE1 + page + BASE2 + industry.getUrlTag() + BASE3;
                URL url = new URL(urlStr);
                URLConnection context = url.openConnection();
                InputStream in = context.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
                char[] cbuf = new char[1024];
                int cnum = -1;
                while ((cnum = br.read(cbuf)) != -1) {
                    html += new String(cbuf, 0, cnum);
                }
                if (html.contains("null")) {
                    break;
                }
                JSONArray array = JSONArray.fromObject(html);
                System.out.println(array);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonCompany = JSONObject.fromObject(array.get(i));
                    System.out.println(jsonCompany.get("symbol") + " " + jsonCompany.get("name"));
                    String numStr=jsonCompany.get("symbol").toString();
                    String name=jsonCompany.get("name").toString();
                    Company company=new Company();
                    company.setNumStr(numStr);
                    company.setName(name);
                    company.setIndustryId(industry.getId());
                    operation.addIndustryCompany(company);
                }
                page++;
            }
        }
    }

    public void setSectionCompany() throws IOException {
        ArrayList<Section> sections = operation.getSections();
        for (Section section:sections) {
            String html = "";
            int page = 1;
            String urlTag = section.getUrlTag();
            while (!html.contains("null")) {
                html = "";
                String urlStr = BASE1 + page + BASE2 + section.getUrlTag() + BASE3;
                URL url = new URL(urlStr);
                URLConnection context = url.openConnection();
                InputStream in = context.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
                char[] cbuf = new char[1024];
                int cnum = -1;
                while ((cnum = br.read(cbuf)) != -1) {
                    html += new String(cbuf, 0, cnum);
                }
                if (html.contains("null")) {
                    break;
                }
                JSONArray array = JSONArray.fromObject(html);
                System.out.println(array);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonCompany = JSONObject.fromObject(array.get(i));
                    System.out.println(jsonCompany.get("symbol") + " " + jsonCompany.get("name"));
                    String numStr=jsonCompany.get("symbol").toString();
                    String name=jsonCompany.get("name").toString();
                    Company company=new Company();
                    company.setNumStr(numStr);
                    company.setName(name);
                    company.setSectionId(section.getId());
                    operation.addSectionCompany(company);
                }
                page++;
            }
        }
    }
    public static void main(String args[]) {
        GetCompany company = new GetCompany();
//        try {
//            company.setSectionCompany();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }
}
