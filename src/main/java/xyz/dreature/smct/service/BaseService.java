package xyz.dreature.smct.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {
    // 查询总数
    int countAll();

    // 查询全表
    List<T> selectAll();

    // 查询随机
    List<T> selectRandom(int count);

    // 查询页面
    List<T> selectByPage(int offset, int limit);

    // 单项查询
    T selectById(ID id);

    // 逐项查询
    List<T> selectByIds(ID... ids);

    // 单批查询
    List<T> selectBatchByIds(List<ID> ids);

    // 分批查询
    List<T> selectBatchByIds(List<ID> ids, int batchSize);

    // 单项插入
    int insert(T obj);

    // 逐项插入
    int insert(T... array);

    // 单批插入
    int insertBatch(List<T> list);

    // 分批插入
    int insertBatch(List<T> list, int batchSize);

    // 单项更新
    int update(T obj);

    // 逐项更新
    int update(T... array);

    // 单批更新
    int updateBatch(List<T> list);

    // 分批更新
    int updateBatch(List<T> list, int batchSize);

    // 单项插入或更新
    int upsert(T obj);

    // 逐项插入或更新
    int upsert(T... array);

    // 单批插入或更新
    int upsertBatch(List<T> list);

    // 分批插入或更新
    int upsertBatch(List<T> list, int batchSize);

    // 单项删除
    int deleteById(ID id);

    // 逐项删除
    int deleteByIds(ID... idArray);

    // 单批删除
    int deleteBatchByIds(List<ID> idList);

    // 分批删除
    int deleteBatchByIds(List<ID> idList, int batchSize);

    // 清空
    void truncate();
}
