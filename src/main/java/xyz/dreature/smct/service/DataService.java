package xyz.dreature.smct.service;

import xyz.dreature.smct.common.entity.Data;

import java.util.List;

public interface DataService extends BaseService<Data, Long> {
    // 生成模拟数据
    List<Data> generateMockData(int count);

    // 生成随机字符串
    String generateRandomString(int length);

    // 解析数据
    List<Data> parseDataFromJsonFile(String filePath);

    // 解析 ID
    List<Long> parseIdsFromString(String ids);
}
