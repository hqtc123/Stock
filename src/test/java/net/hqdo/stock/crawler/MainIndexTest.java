package net.hqdo.stock.crawler;

import net.hqdo.stock.dto.PriceAndDealDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Qing
 * @since 2017/1/4.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MainIndexTest {
    @Autowired
    private MainIndexCrawler mainIndexCrawler;

    @Test
    public void test() throws IOException, URISyntaxException {
        PriceAndDealDto dto = mainIndexCrawler.getSHAPriceAndDeal();
        Assert.assertNotNull(dto);
    }
}
