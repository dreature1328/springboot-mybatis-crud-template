package dreature.smct.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import dreature.smct.entity.Data;
import dreature.smct.mapper.DataMapper;
import dreature.smct.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dreature.smct.common.util.BatchUtils.*;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    private DataMapper dataMapper;

    // 生成数据（测试用）
    public List<Data> generate(int count) {
        // 此处以 UUID 作为 ID，长度为 16 的随机字符串作为属性值为例
        List<Data> dataList = new ArrayList<>(count);
        String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        int length = 16;

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < count; i++) {
            String id = UUID.randomUUID().toString();

            sb.setLength(0);
            for (int j = 0; j < length; j++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            String attr1 = sb.toString();

            sb.setLength(0);
            for (int j = 0; j < length; j++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            String attr2 = sb.toString();

            dataList.add(new Data(id, attr1, attr2));
        }
        return dataList;
    }

    // 解析数据（测试用）
    public List<Data> parse(String filePath) {
        List<Data> dataList = new ArrayList<>();
        try {
            // 读取JSON文件为树结构
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            JsonNode arrayNode = rootNode.path("data");

            // 检查data是否是数组
            if (arrayNode.isArray()) {
                for (JsonNode jsonNode : arrayNode) {
                    // 构建Data对象并添加到列表中
                    Data data = new Data(
                            jsonNode.path("id").asText(),
                            jsonNode.path("key1").asText(),
                            jsonNode.path("key2").asText()
                    );
                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            System.err.println("读取 JSON 文件失败: " + e.getMessage());
        }
        return dataList;
    }

    // 查询总数
    public int countAll() {
        return dataMapper.countAll();
    }

    // 查询全表
    public List<Data> findAll() {
        return dataMapper.findAll();
    }

    // 查询 n 条
    public List<Data> findRandomN(int count) {
        return dataMapper.findRandomN(count);
    }

    // 单项查询
    public List<Data> selectById(String id) {
        return dataMapper.selectById(id);
    }

    // 逐项查询
    public List<Data> selectByIds(String... ids) {
        return mapEach(Arrays.asList(ids), dataMapper::selectById);
    }

    // 单批查询
    public List<Data> selectBatchByIds(List<String> ids) {
        return dataMapper.selectBatchByIds(ids);
    }

    // 分批查询
    public List<Data> selectBatchByIds(List<String> ids, int batchSize) {
        return mapBatch(ids, batchSize, dataMapper::selectBatchByIds);
    }

    // 单项插入
    public int insert(Data data) {
        return dataMapper.insert(data);
    }

    // 逐项插入
    public int insert(Data... dataArray) {
        return reduceEach(Arrays.asList(dataArray), dataMapper::insert);
    }

    // 单批插入
    public int insertBatch(List<Data> dataList) {
        return dataMapper.insertBatch(dataList);
    }

    // 分批插入
    public int insertBatch(List<Data> dataList, int batchSize) {
        return reduceBatch(dataList, batchSize, dataMapper::insertBatch);
    }

    // 单项更新
    public int update(Data data) {
        return dataMapper.update(data);
    }

    // 逐项更新
    public int update(Data... dataArray) {
        return reduceEach(Arrays.asList(dataArray), dataMapper::update);
    }

    // 单批更新
    public int updateBatch(List<Data> dataList) {
        return dataMapper.updateBatch(dataList);
    }

    // 分批更新
    public int updateBatch(List<Data> dataList, int batchSize) {
        return reduceBatch(dataList, batchSize, dataMapper::updateBatch);
    }

    // 单项插入或更新
    public int upsert(Data data) {
        return dataMapper.upsert(data);
    }

    // 逐项插入或更新
    public int upsert(Data... dataArray) {
        return reduceEach(Arrays.asList(dataArray), dataMapper::upsert);
    }

    // 单批插入或更新
    public int upsertBatch(List<Data> dataList) {
        return dataMapper.upsertBatch(dataList);
    }

    // 分批插入或更新
    public int upsertBatch(List<Data> dataList, int batchSize) {
        return reduceBatch(dataList, batchSize, dataMapper::upsertBatch);
    }

    // 单项删除
    public int deleteById(String id) {
        return dataMapper.deleteById(id);
    }

    // 逐项删除
    public int deleteByIds(String... ids) {
        return reduceEach(Arrays.asList(ids), dataMapper::deleteById);
    }

    // 单批删除
    public int deleteBatchByIds(List<String> ids) {
        return dataMapper.deleteBatchByIds(ids);
    }

    // 分批删除
    public int deleteBatchByIds(List<String> ids, int batchSize) {
        return reduceBatch(ids, batchSize, dataMapper::deleteBatchByIds);
    }

    // 清空
    public void truncate() {
        dataMapper.truncate();
    }
}
