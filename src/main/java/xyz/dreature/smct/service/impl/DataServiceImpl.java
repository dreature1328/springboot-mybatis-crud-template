package xyz.dreature.smct.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.common.util.BatchUtils;
import xyz.dreature.smct.mapper.DataMapper;
import xyz.dreature.smct.service.DataService;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
public class DataServiceImpl extends BaseServiceImpl<Data, Long, DataMapper> implements DataService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public DataServiceImpl(DataMapper dataMapper) {
        super(dataMapper);
    }

    // ===== 业务扩展操作 =====
    // 处理全部（游标）
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void processAllWithCursor(Consumer<Data> processor) {
        try (Cursor<Data> cursor = mapper.selectAllWithCursor()) {
            BatchUtils.processEach(cursor, processor);
        } catch (IOException e) {
            throw new RuntimeException("游标处理失败", e);
        }
    }

    // 生成随机字符串
    private String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // 生成模拟数据
    public List<Data> generateMock(int count) {
        SecureRandom random = new SecureRandom();
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

    // 解析 JSON 文件
    public List<Data> parseJsonFile(String filePath) {
        List<Data> dataList = new ArrayList<>();
        try {
            // 读取 JSON 文件为树结构
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            JsonNode arrayNode = rootNode.path("data");

            // 检查 "data" 键的值是否是数组
            if (arrayNode.isArray()) {
                for (JsonNode jsonNode : arrayNode) {
                    Data data = new Data();

                    // 解析并设置所有字段
                    data.setId(jsonNode.get("id").asLong());
                    data.setNumericValue(jsonNode.path("numericValue").asInt());
                    data.setDecimalValue(jsonNode.path("decimalValue").asDouble());
                    data.setTextContent(jsonNode.path("textContent").asText());
                    data.setActiveFlag(jsonNode.path("activeFlag").asBoolean());

                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            log.error("JSON 解析失败: {}", e);
            throw new RuntimeException("JSON 文件解析失败", e);
        }
        return dataList;
    }
}
