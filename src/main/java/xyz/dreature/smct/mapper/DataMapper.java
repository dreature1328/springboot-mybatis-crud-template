package xyz.dreature.smct.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.dreature.smct.common.entity.Data;

@Mapper
public interface DataMapper extends BaseMapper<Data, Long> {
    // ===== 业务扩展操作 =====
}
