package com.hq.network;

import com.hq.database.DataOperation;
import com.hq.model.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-28
 * Time: 下午8:02
 * To change this template use File | Settings | File Templates.
 */
public class GetCompanySixData {
    String database = "";
    DataOperation operation = null;
    final String STOCK_1 = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_StockStructure/stockid/";
    final String STOCK_2 = ".phtml";

    final String DEBT_1 = "http://money.finance.sina.com.cn/corp/go.php/vFD_BalanceSheet/stockid/";
    final String DEBT_2 = "/ctrl/part/displaytype/4.phtml";

    final String PER_1 = "http://vip.stock.finance.sina.com.cn/corp/go.php/vFD_FinanceSummary/stockid/";
    final String PER_2 = "/displaytype/4.phtml";

    public GetCompanySixData() {
        init();
    }

    public void init() {
        database = "stock_sys";
        operation = new DataOperation(database);
    }

    /**
     * map company data company_six
     */
    public void addCompanySix() {
        ArrayList<Company> companies = operation.getCompanies();
        for (Company company : companies) {
            operation.addCompanySix(company.getId());
        }
    }

    /**
     * get stock_all and stock_A
     */
    public void getStockAllAndA() {
        ArrayList<Company> companies = operation.getCompanies();
        for (Company company : companies) {
            String num = company.getNumStr().substring(2);
            String url = STOCK_1 + num + STOCK_2;
            try {
                Document doc = Jsoup.connect(url).get();
                System.out.println(doc.title());

                //stock all 总股数
                Elements ele1 = doc.select("#StockStructureNewTable0 tbody tr:eq(4) td:eq(1)");
                int index = ele1.html().indexOf('万');
                if (index == -1) {
                    return;
                }
                double stockAll = Double.parseDouble(ele1.html().substring(0, index));
                //stock A 流通A股
                Elements ele2 = doc.select("#StockStructureNewTable0 tbody tr:eq(6) td:eq(1)");
                index = ele2.html().indexOf('万');
                double stockA = Double.parseDouble(ele2.html().substring(0, index));
                operation.updateStockAandAll(company.getNumStr(), stockAll, stockA);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    /**
     * get current debt and long debt
     */
    public void getDebt() {
        ArrayList<Company> companies = operation.getCompanies();
        for (Company company : companies) {
            String num = company.getNumStr().substring(2);
            String url = DEBT_1 + num + DEBT_2;
            try {
                Document doc = Jsoup.connect(url).get();
                System.out.println(doc.title());

                double currentDebt = 0, longDebt = 0, bossProfit = 0;
                Elements links = doc.select("td a");
                boolean hasCurrent = false;
                for (Element link : links) {
                    if (link.html().equals("流动负债合计")) {
                        Element ele = link.parent().nextElementSibling();
                        if (ele.html().contains("--")) {
                            currentDebt = 0;
                        } else {
                            currentDebt = Double.parseDouble(ele.html().replaceAll(",", ""));
                        }
                        hasCurrent = true;
                    }
                    if (link.html().equals("非流动负债合计")) {
                        Element ele = link.parent().nextElementSibling();
                        if (ele.html().contains("--")) {
                            longDebt = 0;
                        } else {
                            longDebt = Double.parseDouble(ele.html().replaceAll(",", ""));
                        }
                    }
                    if (link.html().equals("负债合计")) {
                        if (hasCurrent == false) {
                            Element ele = link.parent().nextElementSibling();
                            if (ele.html().contains("--")) {
                                longDebt = 0;
                                currentDebt = 0;
                            } else {
                                longDebt = 0;
                                currentDebt = Double.parseDouble(ele.html().replaceAll(",", ""));
                            }
                        }
                    }
                    if (link.html().equals("所有者权益(或股东权益)合计") || link.html().equals("股东权益合计")) {
                        Element ele = link.parent().nextElementSibling();
                        if (ele.html().contains("--")) {
                            bossProfit = 0;
                        } else {
                            bossProfit = currentDebt = Double.parseDouble(ele.html().replaceAll(",", ""));
                        }
                    }
                }
                //current debt
//                Elements ele1 = doc.select("#BalanceSheetNewTable0 tbody tr:eq(88) td:eq(1)");
//                if (ele1.html().length() == 0) {
//                    return;
//                }
//
//                if (ele1.html().contains(",,")) {
//                    return;
//                }
//                if (ele1.html().contains("--")) {
//                    currentDebt = -1;
//                } else {
//                    currentDebt = Double.parseDouble(ele1.html().replaceAll(",", ""));
//                }


                operation.updateDebt(company.getId(), currentDebt, longDebt, bossProfit);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void getPerValue() {
        ArrayList<Company> companies = operation.getCompanies();
        for (Company company : companies) {
            String num = company.getNumStr().substring(2);
            String url = PER_1 + num + PER_2;
            try {
                Document doc = Jsoup.connect(url).get();
                System.out.println(doc.title());

                double perValue = 0;
                Elements tDs = doc.select("td");
                for (Element td : tDs) {
                    if (td.html().equals("每股净资产")) {
                        Element td2 = td.nextElementSibling();
                        System.out.println(td2.html());

                        int index = td2.html().indexOf('元');
                        if (index == -1) {
                            return;
                        }
                        int index2=td2.html().indexOf('>');
                        String value=td2.html().substring(index2, index);
                        System.out.println(value);
                        perValue = Double.parseDouble(td2.html().substring(index2+1, index));
                        break;
                    }

                }

                operation.updatePerValue(company.getId(), perValue);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static void main(String args[]) {
        GetCompanySixData get = new GetCompanySixData();
//        get.getStockAllAndA();
//        get.getDebt();
        get.getPerValue();
    }
}
