package dreature.smct.service;

import dreature.smct.entity.Data;

import java.util.List;

public interface DataService {

    // 生成对象
    List<Data> generate(int count);

    // 读取对象
    List<Data> read(String filePath);

    // 查询总数
    int countAll();

    // 查询 n 条
    List<Data> findTopN(int n);

    // 单项查询
    List<Data> selectById(String id);

    // 依次查询
    List<Data> selectById(String... ids);

    // 批量查询
    List<Data> selectBatchByIds(List<String> ids);

    // 分页查询
    List<Data> selectPageByIds(List<String> ids);

    // 单项插入
    int insert(Data data);

    // 依次插入
    int insert(Data... dataArray);

    // 批量插入
    int insertBatch(List<Data> dataList);

    // 分页插入
    int insertPage(List<Data> dataList);

    // 单项更新
    int update(Data data);

    // 依次更新
    int update(Data... dataArray);

    // 批量更新
    int updateBatch(List<Data> dataList);

    // 分页更新
    int updatePage(List<Data> dataList);

    // 单项插入或更新
    int upsert(Data data);

    // 依次插入或更新
    int upsert(Data... dataArray);

    // 批量插入或更新
    int upsertBatch(List<Data> dataList);

    // 分页插入或更新
    int upsertPage(List<Data> dataList);

    // 单项删除
    int deleteById(String id);

    // 依次删除
    int deleteById(String... idArray);

    // 批量删除
    int deleteBatchByIds(List<String> idList);

    // 分页删除
    int deletePageByIds(List<String> idList);

    // 清空
    void truncate();
}
