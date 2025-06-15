package dreature.smct.service;

import dreature.smct.entity.Data;

import java.util.List;

public interface DataService {

    // 生成数据（测试用）
    List<Data> generate(int count);

    // 解析数据（测试用）
    List<Data> parse(String filePath);

    // 查询总数
    int countAll();

    // 查询全表
    List<Data> findAll();

    // 查询 n 条
    List<Data> findRandomN(int count);

    // 单项查询
    List<Data> selectById(String id);

    // 逐项查询
    List<Data> selectByIds(String... ids);

    // 单批查询
    List<Data> selectBatchByIds(List<String> ids);

    // 分批查询
    List<Data> selectBatchByIds(List<String> ids, int batchSize);

    // 单项插入
    int insert(Data data);

    // 逐项插入
    int insert(Data... dataArray);

    // 单批插入
    int insertBatch(List<Data> dataList);

    // 分批插入
    int insertBatch(List<Data> dataList, int batchSize);

    // 单项更新
    int update(Data data);

    // 逐项更新
    int update(Data... dataArray);

    // 单批更新
    int updateBatch(List<Data> dataList);

    // 分批更新
    int updateBatch(List<Data> dataList, int batchSize);

    // 单项插入或更新
    int upsert(Data data);

    // 逐项插入或更新
    int upsert(Data... dataArray);

    // 单批插入或更新
    int upsertBatch(List<Data> dataList);

    // 分批插入或更新
    int upsertBatch(List<Data> dataList, int batchSize);

    // 单项删除
    int deleteById(String id);

    // 逐项删除
    int deleteByIds(String... idArray);

    // 单批删除
    int deleteBatchByIds(List<String> idList);

    // 分批删除
    int deleteBatchByIds(List<String> idList, int batchSize);

    // 清空
    void truncate();
}
