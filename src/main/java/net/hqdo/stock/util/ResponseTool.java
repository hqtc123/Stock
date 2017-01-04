package net.hqdo.stock.util;

import net.hqdo.stock.model.DealInfoEntity;
import net.hqdo.stock.model.PriceEntity;

/**
 * @author Qing
 * @since 2017/1/4.
 */
public class ResponseTool {
    public static PriceEntity priceBySina(String rowStr) {
        String[] strArr = rowStr.split("=")[1].split(",");
        PriceEntity entity = new PriceEntity();

        entity.setCode(strArr[0]);
        return entity;
    }

    public static DealInfoEntity dealInfoBySina(String rowStr) {
        return new DealInfoEntity();
    }

    public static PriceEntity priceByHx(String rowStr) {
        return new PriceEntity();
    }

    public static DealInfoEntity dealInfoByHx(String rowStr) {
        return new DealInfoEntity();
    }
}
