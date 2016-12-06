package net.hqdo.stock.mapper;

import net.hqdo.stock.model.CompanyEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Qing on 2016/12/6.
 */
@Component
public interface CompanyMapper extends Mapper<CompanyEntity> {
}
