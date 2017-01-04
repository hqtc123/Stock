package net.hqdo.stock.crawler;

import net.hqdo.stock.dto.PriceAndDealDto;
import net.hqdo.stock.model.DealInfoEntity;
import net.hqdo.stock.model.PriceEntity;
import net.hqdo.stock.util.ResponseTool;
import net.hqdo.stock.util.constant.MarketCode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Qing
 * @since 2017/1/4.
 */
@Component
public class MainIndexCrawler {
    private final String SINA_BASE_URL = "http://hq.sinajs.cn/list=";

    public PriceAndDealDto getSHAPriceAndDeal() throws IOException, URISyntaxException {
        String response = HttpTool.doHttpGet(SINA_BASE_URL + "s_" + MarketCode.SHA);
        PriceEntity price = ResponseTool.priceBySina(response);
        DealInfoEntity dealInfo = new ResponseTool().dealInfoBySina(response);
        return new PriceAndDealDto(price, dealInfo);
    }
}
