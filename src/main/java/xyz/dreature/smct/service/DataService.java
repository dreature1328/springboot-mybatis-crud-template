package xyz.dreature.smct.service;

import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.mapper.DataMapper;

import java.util.List;
import java.util.function.Consumer;

public interface DataService extends BaseService<Data, Long, DataMapper> {
    // ====== 业务扩展操作 =====
    // 处理全部（游标）
    void processAllWithCursor(Consumer<Data> processor);

    // 生成模拟数据
    List<Data> generateMock(int count);

    // 解析 JSON 文件
    List<Data> parseJsonFile(String filePath);
}
