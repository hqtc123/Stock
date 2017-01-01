package net.hqdo.stock.mapper;

import net.hqdo.stock.model.PriceEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Qing
 * @since 2016/12/6.
 */
@Component
public interface PriceMapper extends Mapper<PriceEntity> {
}
