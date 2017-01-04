package net.hqdo.stock.dto;

import net.hqdo.stock.model.DealInfoEntity;
import net.hqdo.stock.model.PriceEntity;

/**
 * @author Qing
 * @since 2017/1/4.
 */
public class PriceAndDealDto {
    private PriceEntity priceEntity;
    private DealInfoEntity dealInfoEntity;

    public PriceAndDealDto(PriceEntity priceEntity, DealInfoEntity dealInfoEntity) {
        this.priceEntity = priceEntity;
        this.dealInfoEntity = dealInfoEntity;
    }

    public PriceEntity getPriceEntity() {
        return priceEntity;
    }

    public DealInfoEntity getDealInfoEntity() {
        return dealInfoEntity;
    }

}
