package com.springboot.data.service;

import com.springboot.data.common.pojo.Data;
import com.springboot.data.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


@Service
public class DataService {
    @Autowired
    private DataMapper dataMapper;

    // 分页处理
    public <T> void pageHandle(List<T> list, int pageSize, Consumer<List<T>> handleFunction){
        int count = 0;
        while (!list.isEmpty()) {
            // 取出当前页数据
            List<T> subList = list.subList(0, Math.min(pageSize, list.size()));
            // 执行插入或更新操作
            handleFunction.accept(subList);
            // 统计插入或更新的记录数
            count += subList.size();
            // 从列表中移除已处理的数据
            list.subList(0, subList.size()).clear();
        }
    }

    // 分页处理
    public <T, R> List<R> pageHandle(List<T> list, int pageSize, Function<List<T>, List<R>> handleFunction) {
        List<R> resultList = new ArrayList<>();
        int count = 0;
        while (!list.isEmpty()) {
            // 取出当前页数据
            List<T> subList = list.subList(0, Math.min(pageSize, list.size()));
            // 执行查询操作
            List<R> subResultList = handleFunction.apply(subList);
            // 将结果添加到总结果列表中
            resultList.addAll(subResultList);
            // 统计查询的记录数
            count += subList.size();
            // 从列表中移除已处理的数据
            list.subList(0, subList.size()).clear();
        }
        return resultList;
    }

    // 生成对象
    public List<Data> generateDatas() {

        // 自己按需求生成自定义对象列表
        List<Data> dataList = new ArrayList<>();

        Data data1 = new Data();
        Data data2 = new Data();
        Data data3 = new Data();

        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);

        return dataList;
    }

    // 单项查询
    public Data selectData(String id) {
        return dataMapper.selectData(id);
    }

    // 依次查询
    public List<Data> selectData(String... idArray) {
        List<Data> dataList = new ArrayList<>();
        for(String id : idArray){
            dataList.add(dataMapper.selectData(id));
        }
        return dataList;
    }

    // 批量查询
    public List<Data> batchSelectData(List<String> idList) {
        return dataMapper.batchSelectData(idList);
    }

    // 分页查询
    public List<Data> pageSelectData(List<String> idList) {
        int pageDataSize = 12000; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = Data.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        return pageHandle(idList, pageSize, dataMapper::batchSelectData);
    }

    // 单项插入
    public void insertData(Data data) {
        dataMapper.insertData(data);
        return ;
    }

    // 依次插入
    public void insertData(Data... dataArray) {
        for(Data data : dataArray){
            dataMapper.insertData(data);
        }
        return ;
    }

    // 批量插入
    public void batchInsertData(List<Data> dataList) {
        dataMapper.batchInsertData(dataList);
        return ;
    }

    // 分页插入
    public void pageInsertData(List<Data> dataList) {
        int pageDataSize = 12000; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = Data.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle(dataList, pageSize, dataMapper::batchInsertData);
        return ;
    }

    // 单项更新
    public void updateData(Data data) {
        dataMapper.updateData(data);
        return ;
    }

    // 依次更新
    public void updateData(Data... dataArray) {
        for(Data data : dataArray){
            dataMapper.updateData(data);
        }
        return ;
    }

    // 批量更新
    public void batchUpdateData(List<Data> dataList) {
        dataMapper.batchUpdateData(dataList);
        return ;
    }

    // 分页更新
    public void pageUpdateData(List<Data> dataList) {
        int pageDataSize = 12000; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = Data.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle(dataList, pageSize, dataMapper::batchUpdateData);
        return ;
    }

    // 单项插入或更新
    public void insertOrUpdateData(Data data) {
        dataMapper.insertOrUpdateData(data);
        return ;
    }

    // 依次插入或更新
    public void insertOrUpdateData(Data... dataArray) {
        for(Data data : dataArray){
            dataMapper.insertOrUpdateData(data);
        }
        return ;
    }

    // 批量插入或更新
    public void batchInsertOrUpdateData(List<Data> dataList) {
        dataMapper.batchInsertOrUpdateData(dataList);
        return ;
    }

    // 分页插入或更新
    public void pageInsertOrUpdateData(List<Data> dataList) {
        int pageDataSize = 12000; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = Data.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle(dataList, pageSize, dataMapper::batchInsertOrUpdateData);
        return ;
    }

    // 单项删除
    public void deleteData(String id) {
        dataMapper.deleteData(id);
        return ;
    }

    // 依次删除
    public void deleteData(String... idArray) {
        for(String id : idArray){
            dataMapper.deleteData(id);
        }
        return ;
    }

    // 批量删除
    public void batchDeleteData(List<String> idList) {
        dataMapper.batchDeleteData(idList);
        return ;
    }

    // 分页删除
    public void pageDeleteData(List<String> idList) {
        int pageDataSize = 12000; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = Data.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle(idList, pageSize, dataMapper::batchDeleteData);
        return ;
    }

    // 清空
    public void clearData(){
        dataMapper.clearData();
        return ;
    }

}
