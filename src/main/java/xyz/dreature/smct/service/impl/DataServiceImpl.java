package xyz.dreature.smct.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataProcessingException;
import org.springframework.stereotype.Service;
import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.service.DataService;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data, Long> implements DataService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private SecureRandom random = new SecureRandom();

    // 生成模拟数据
    public List<Data> generateMockData(int count) {
        List<Data> dataList = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            Data data = new Data();

            // 取 UUID 的高 64 位并转换为非负长整型值
            data.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));

            // 取 0-10000 之间的随机整数
            data.setNumericValue(random.nextInt(10001));

            // 取 0.0-100.0 之间的随机小数（保留两位）
            data.setDecimalValue(Math.round(random.nextDouble() * 100 * 100.0) / 100.0);

            // 取 16 位随机字符串（大小写字母及数字）
            data.setTextContent(generateRandomString(16));

            // 取随机布尔值
            data.setActiveFlag(random.nextBoolean());

            dataList.add(data);
        }
        return dataList;
    }

    // 生成随机字符串
    public String generateRandomString(int length) {
        String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // 解析 JSON 文件
    public List<Data> parseDataFromJsonFile(String filePath) {
        List<Data> dataList = new ArrayList<>();
        try {
            // 读取 JSON 文件为树结构
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            JsonNode arrayNode = rootNode.path("data");

            // 检查 "data" 键的值是否是数组
            if (arrayNode.isArray()) {
                for (JsonNode jsonNode : arrayNode) {
                    Data data = new Data();

                    // 解析并设置所有属性
                    data.setId(jsonNode.get("id").asLong());
                    data.setNumericValue(jsonNode.path("numericValue").asInt());
                    data.setDecimalValue(jsonNode.path("decimalValue").asDouble());
                    data.setTextContent(jsonNode.path("textContent").asText());
                    data.setActiveFlag(jsonNode.path("activeFlag").asBoolean());

                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            System.err.println("读取 JSON 文件失败: " + e.getMessage());
            throw new DataProcessingException("JSON 文件解析失败", e);
        }
        return dataList;
    }

    // 解析 ID
    public List<Long> parseIdsFromString(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try {
                        return Long.parseLong(s);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("无效的 ID 格式: " + s);
                    }
                })
                .collect(Collectors.toList());
    }
}
