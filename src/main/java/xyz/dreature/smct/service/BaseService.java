package xyz.dreature.smct.service;

import org.apache.ibatis.cursor.Cursor;
import xyz.dreature.smct.mapper.base.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, ID extends Serializable, M extends BaseMapper<T, ID>> {
    // ===== 查询基础操作 =====
    // 查询总数
    int countAll();

    // 查询全表
    List<T> selectAll();

    // 查询全表（游标）
    Cursor<T> selectAllWithCursor();

    // 查询随机
    List<T> selectRandom(int limit);

    // 查询页面
    List<T> selectByPage(int offset, int limit);

    // 条件查询
    List<T> selectByCondition(Map<String, Object> condition);

    // 单项查询
    T selectById(ID id);

    // 逐项查询
    List<T> selectByIds(ID... ids);

    // 逐项查询
    List<T> selectByIds(List<ID> ids);

    // 单批查询
    List<T> selectBatchByIds(List<ID> ids);

    // 分批查询
    List<T> selectBatchByIds(List<ID> ids, int batchSize);

    // ===== 插入基础操作 =====
    // 单项插入
    int insert(T entity);

    // 逐项插入
    int insert(T... entities);

    // 逐项插入
    int insert(List<T> entities);

    // 单批插入
    int insertBatch(List<T> entities);

    // 分批插入
    int insertBatch(List<T> entities, int batchSize);

    // ===== 更新基础操作 =====
    // 单项更新
    int update(T entity);

    // 逐项更新
    int update(T... entities);

    // 逐项更新
    int update(List<T> entities);

    // 单批更新
    int updateBatch(List<T> entities);

    // 分批更新
    int updateBatch(List<T> entities, int batchSize);

    // ===== 插入或更新基础操作 =====
    // 单项插入或更新
    int upsert(T entity);

    // 逐项插入或更新
    int upsert(T... entities);

    // 逐项插入或更新
    int upsert(List<T> entities);

    // 单批插入或更新
    int upsertBatch(List<T> entities);

    // 分批插入或更新
    int upsertBatch(List<T> entities, int batchSize);

    // ===== 删除基础操作 =====
    // 单项删除
    int deleteById(ID id);

    // 逐项删除
    int deleteByIds(ID... ids);

    // 逐项删除
    int deleteByIds(List<ID> ids);

    // 单批删除
    int deleteBatchByIds(List<ID> ids);

    // 分批删除
    int deleteBatchByIds(List<ID> ids, int batchSize);

    // ===== 其他操作 =====
    // 清空
    void truncate();
}
