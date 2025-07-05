package xyz.dreature.smct.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreature.smct.common.util.BatchUtils;
import xyz.dreature.smct.mapper.BaseMapper;
import xyz.dreature.smct.service.BaseService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
    @Autowired
    protected SqlSession sqlSession;
    @Autowired
    protected BaseMapper<T, ID> baseMapper;

    // 查询总数
    public int countAll() {
        return baseMapper.countAll();
    }

    // 查询全表
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    // 查询随机
    public List<T> selectRandom(int count) {
        return baseMapper.selectRandom(count);
    }

    // 查询页面
    public List<T> selectByPage(int offset, int limit) {
        return baseMapper.selectByPage(offset, limit);
    }

    // 单项查询
    public T selectById(ID id) {
        return baseMapper.selectById(id);
    }

    // 逐项查询
    public List<T> selectByIds(ID... ids) {
        return BatchUtils.mapEach(Arrays.asList(ids), baseMapper::selectById);
    }

    // 单批查询
    public List<T> selectBatchByIds(List<ID> ids) {
        return baseMapper.selectBatchByIds(ids);
    }

    // 分批查询
    public List<T> selectBatchByIds(List<ID> ids, int batchSize) {
        return BatchUtils.flatMapBatch(ids, batchSize, baseMapper::selectBatchByIds);
    }

    // 单项插入
    public int insert(T obj) {
        return baseMapper.insert(obj);
    }

    // 逐项插入
    public int insert(T... array) {
        return BatchUtils.reduceEachToInt(Arrays.asList(array), baseMapper::insert);
    }

    // 单批插入
    public int insertBatch(List<T> list) {
        return baseMapper.insertBatch(list);
    }

    // 分批插入
    public int insertBatch(List<T> list, int batchSize) {
        return BatchUtils.reduceBatchToInt(list, batchSize, baseMapper::insertBatch);
    }

    // 单项更新
    public int update(T obj) {
        return baseMapper.update(obj);
    }

    // 逐项更新
    public int update(T... array) {
        return BatchUtils.reduceEachToInt(Arrays.asList(array), baseMapper::update);
    }

    // 单批更新
    public int updateBatch(List<T> list) {
        return baseMapper.updateBatch(list);
    }

    // 分批更新
    public int updateBatch(List<T> list, int batchSize) {
        return BatchUtils.reduceBatchToInt(list, batchSize, baseMapper::updateBatch);
    }

    // 单项插入或更新
    public int upsert(T obj) {
        return baseMapper.upsert(obj);
    }

    // 逐项插入或更新
    public int upsert(T... array) {
        return BatchUtils.reduceEachToInt(Arrays.asList(array), baseMapper::upsert);
    }

    // 单批插入或更新
    public int upsertBatch(List<T> list) {
        return baseMapper.upsertBatch(list);
    }

    // 分批插入或更新
    public int upsertBatch(List<T> list, int batchSize) {
        return BatchUtils.reduceBatchToInt(list, batchSize, baseMapper::upsertBatch);
    }

    // 单项删除
    public int deleteById(ID id) {
        return baseMapper.deleteById(id);
    }

    // 逐项删除
    public int deleteByIds(ID... ids) {
        return BatchUtils.reduceEachToInt(Arrays.asList(ids), baseMapper::deleteById);
    }

    // 单批删除
    public int deleteBatchByIds(List<ID> ids) {
        return baseMapper.deleteBatchByIds(ids);
    }

    // 分批删除
    public int deleteBatchByIds(List<ID> ids, int batchSize) {
        return BatchUtils.reduceBatchToInt(ids, batchSize, baseMapper::deleteBatchByIds);
    }

    // 清空
    public void truncate() {
        baseMapper.truncate();
    }
}
