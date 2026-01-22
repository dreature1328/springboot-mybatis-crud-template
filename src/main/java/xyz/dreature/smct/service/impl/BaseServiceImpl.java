package xyz.dreature.smct.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.dreature.smct.common.util.BatchUtils;
import xyz.dreature.smct.mapper.base.BaseMapper;
import xyz.dreature.smct.service.BaseService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable, M extends BaseMapper<T, ID>> implements BaseService<T, ID, M> {
    // ORM 映射器
    protected M mapper;

    public BaseServiceImpl(M mapper) {
        this.mapper = mapper;
    }

    // ===== 查询基础操作 =====
    // 查询总数
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int countAll() {
        return mapper.countAll();
    }

    // 查询全表
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    // 查询全表（游标）
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Cursor<T> selectAllWithCursor() {
        return mapper.selectAllWithCursor();
    }

    // 查询随机
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectRandom(int limit) {
        return mapper.selectRandom(limit);
    }

    // 查询页面
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectByPage(int offset, int limit) {
        return mapper.selectByPage(offset, limit);
    }

    // 条件查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectByCondition(Map<String, Object> condition) {
        return mapper.selectByCondition(condition);
    }

    // 单项查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public T selectById(ID id) {
        return mapper.selectById(id);
    }

    // 逐项查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectByIds(ID... ids) {
        return BatchUtils.mapEach(Arrays.asList(ids), mapper::selectById);
    }

    // 逐项查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectByIds(List<ID> ids) {
        return BatchUtils.mapEach(ids, mapper::selectById);
    }

    // 单批查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectBatchByIds(List<ID> ids) {
        return mapper.selectBatchByIds(ids);
    }

    // 分批查询
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<T> selectBatchByIds(List<ID> ids, int batchSize) {
        return BatchUtils.flatMapBatch(ids, batchSize, mapper::selectBatchByIds);
    }

    // ===== 插入基础操作 =====
    // 单项插入
    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    // 逐项插入
    @Override
    public int insert(T... entities) {
        return BatchUtils.reduceEachToInt(Arrays.asList(entities), mapper::insert);
    }

    // 逐项插入
    @Override
    public int insert(List<T> entities) {
        return BatchUtils.reduceEachToInt(entities, mapper::insert);
    }

    // 单批插入
    @Override
    public int insertBatch(List<T> entities) {
        return mapper.insertBatch(entities);
    }

    // 分批插入
    @Override
    public int insertBatch(List<T> entities, int batchSize) {
        return BatchUtils.reduceBatchToInt(entities, batchSize, mapper::insertBatch);
    }

    // ===== 更新基础操作 =====
    // 单项更新
    @Override
    public int update(T entity) {
        return mapper.update(entity);
    }

    // 逐项更新
    @Override
    public int update(T... entities) {
        return BatchUtils.reduceEachToInt(Arrays.asList(entities), mapper::update);
    }

    // 逐项更新
    @Override
    public int update(List<T> entities) {
        return BatchUtils.reduceEachToInt(entities, mapper::update);
    }

    // 单批更新
    @Override
    public int updateBatch(List<T> entities) {
        return mapper.updateBatch(entities);
    }

    // 分批更新
    @Override
    public int updateBatch(List<T> entities, int batchSize) {
        return BatchUtils.reduceBatchToInt(entities, batchSize, mapper::updateBatch);
    }

    // ===== 插入或更新基础操作 =====
    // 单项插入或更新
    @Override
    public int upsert(T entity) {
        return mapper.upsert(entity);
    }

    // 逐项插入或更新
    @Override
    public int upsert(T... entities) {
        return BatchUtils.reduceEachToInt(Arrays.asList(entities), mapper::upsert);
    }

    // 逐项插入或更新
    @Override
    public int upsert(List<T> entities) {
        return BatchUtils.reduceEachToInt(entities, mapper::upsert);
    }

    // 单批插入或更新
    @Override
    public int upsertBatch(List<T> entities) {
        return mapper.upsertBatch(entities);
    }

    // 分批插入或更新
    @Override
    public int upsertBatch(List<T> entities, int batchSize) {
        return BatchUtils.reduceBatchToInt(entities, batchSize, mapper::upsertBatch);
    }

    // ===== 删除基础操作 =====
    // 单项删除
    @Override
    public int deleteById(ID id) {
        return mapper.deleteById(id);
    }

    // 逐项删除
    @Override
    public int deleteByIds(ID... ids) {
        return BatchUtils.reduceEachToInt(Arrays.asList(ids), mapper::deleteById);
    }

    // 逐项删除
    @Override
    public int deleteByIds(List<ID> ids) {
        return BatchUtils.reduceEachToInt(ids, mapper::deleteById);
    }

    // 单批删除
    @Override
    public int deleteBatchByIds(List<ID> ids) {
        return mapper.deleteBatchByIds(ids);
    }

    // 分批删除
    @Override
    public int deleteBatchByIds(List<ID> ids, int batchSize) {
        return BatchUtils.reduceBatchToInt(ids, batchSize, mapper::deleteBatchByIds);
    }

    // ===== 其他操作 =====
    // 清空
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void truncate() {
        mapper.truncate();
    }
}
