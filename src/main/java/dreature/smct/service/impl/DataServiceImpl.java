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
import java.util.List;
import java.util.UUID;

import static dreature.smct.common.util.BatchUtils.*;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    private DataMapper dataMapper;

    // 生成对象（测试用）
    public List<Data> generate(int count) {
        // 此处以 UUID 作为 ID，长度为 16 的随机字符串作为属性值为例
        List<Data> dataList = new ArrayList<>(count);
        String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        int length = 16;

        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < count; i++) {
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

    // 读取对象（测试用）
    public List<Data> read(String filePath) {
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

    // 计算页面大小
    public int calculatePageSize(Class<?> clazz) {
        int cells = 12000; // 每页单元格数，即行数 × 列数
        int fields = clazz.getDeclaredFields().length; // 列数
        int pageSize = cells / fields; // 页面大小，即每页行数
        return pageSize;
    }

    // 查询总数
    public int countAll() {
        return dataMapper.countAll();
    };

    // 查询 n 条
    public List<Data> findTopN(int n) {
        return dataMapper.findTopN(n);
    }

    // 单项查询
    public List<Data> selectById(String id) {
        return dataMapper.selectById(id);
    }

    // 依次查询
    public List<Data> selectById(String... ids) {
        List<Data> result = new ArrayList<>();
        for(String id : ids){
            List<Data> subResult = dataMapper.selectById(id);
            result.addAll(subResult);
        }
        return result;
    }

    // 批量查询
    public List<Data> selectBatchByIds(List<String> ids) {
        return dataMapper.selectBatchByIds(ids);
    }

    // 分页查询
    public List<Data> selectPageByIds(List<String> ids) {
        int pageSize = calculatePageSize(Data.class);
        return mapPage(ids, pageSize, dataMapper::selectBatchByIds);
    }

    // 单项插入
    public int insert(Data data) {
        int affectedRows = dataMapper.insert(data);
        return affectedRows;
    }

    // 依次插入
    public int insert(Data... dataArray) {
        int affectedRows = 0;
        for(Data data : dataArray){
            affectedRows += dataMapper.insert(data);
        }
        return affectedRows;
    }

    // 批量插入
    public int insertBatch(List<Data> dataList) {
        int affectedRows = dataMapper.insertBatch(dataList);
        return affectedRows;
    }

    // 分页插入
    public int insertPage(List<Data> dataList) {
        int pageSize = calculatePageSize(Data.class);
        int affectedRows = reducePage(dataList, pageSize, dataMapper::insertBatch);
        return affectedRows;
    }

    // 单项更新
    public int update(Data data) {
        int affectedRows = dataMapper.update(data);
        return affectedRows;
    }

    // 依次更新
    public int update(Data... dataArray) {
        int affectedRows = 0;
        for(Data data : dataArray){
            affectedRows += dataMapper.update(data);
        }
        return affectedRows;
    }

    // 批量更新
    public int updateBatch(List<Data> dataList) {
        int affectedRows = dataMapper.updateBatch(dataList);
        return affectedRows;
    }

    // 分页更新
    public int updatePage(List<Data> dataList) {
        int pageSize = calculatePageSize(Data.class);
        int affectedRows = reducePage(dataList, pageSize, dataMapper::updateBatch);
        return affectedRows;
    }

    // 单项插入或更新
    public int upsert(Data data) {
        int affectedRows = dataMapper.upsert(data);
        return affectedRows;
    }

    // 依次插入或更新
    public int upsert(Data... dataArray) {
        int affectedRows = 0;
        for(Data data : dataArray){
            affectedRows += dataMapper.upsert(data);
        }
        return affectedRows;
    }

    // 批量插入或更新
    public int upsertBatch(List<Data> dataList) {
        int affectedRows = dataMapper.upsertBatch(dataList);
        return affectedRows;
    }

    // 分页插入或更新
    public int upsertPage(List<Data> dataList) {
        int pageSize = calculatePageSize(Data.class);
        int affectedRows = reducePage(dataList, pageSize, dataMapper::upsertBatch);
        return affectedRows;
    }

    // 单项删除
    public int deleteById(String id) {
        int affectedRows = dataMapper.deleteById(id);
        return affectedRows;
    }

    // 依次删除
    public int deleteById(String... idArray) {
        int affectedRows = 0;
        for(String id : idArray){
            affectedRows += dataMapper.deleteById(id);
        }
        return affectedRows;
    }

    // 批量删除
    public int deleteBatchByIds(List<String> ids) {
        int affectedRows = dataMapper.deleteBatchByIds(ids);
        return affectedRows;
    }

    // 分页删除
    public int deletePageByIds(List<String> ids) {
        int pageSize = calculatePageSize(Data.class);
        int affectedRows = reducePage(ids, pageSize, dataMapper::deleteBatchByIds);
        return affectedRows;
    }

    // 清空
    public void truncate(){
        dataMapper.truncate();
        return ;
    }
}
