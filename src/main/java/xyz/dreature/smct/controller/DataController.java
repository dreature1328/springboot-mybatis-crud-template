package xyz.dreature.smct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.common.vo.Result;
import xyz.dreature.smct.service.DataService;

import java.util.List;

// 测试接口
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    // 生成模拟数据
    @RequestMapping("/generate-mock")
    public ResponseEntity<Result<List<Data>>> generateMockData(
            @RequestParam(name = "count", defaultValue = "10") int count
    ) {
        List<Data> result = dataService.generateMockData(count);
        int resultCount = result.size();
        String message = String.format("生成 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 解析 JSON 数据
    @RequestMapping("/parse-json")
    public ResponseEntity<Result<List<Data>>> parseDataFromJsonFile() {
        List<Data> result = dataService.parseDataFromJsonFile("script/mock_data.json");
        int resultCount = result.size();
        String message = String.format("解析 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询总数
    @RequestMapping("/count-all")
    public ResponseEntity<Result<Integer>> countAll() {
        int count = dataService.countAll();
        String message = String.format("查询总数为 %d 条", count);
        return ResponseEntity.ok().body(Result.success(message, count));
    }

    // 查询全表
    @RequestMapping("/select-all")
    public ResponseEntity<Result<List<Data>>> selectAll() {
        List<Data> result = dataService.selectAll();
        int resultCount = result.size();
        String message = String.format("全表查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询随机
    @RequestMapping("/select-random")
    public ResponseEntity<Result<List<Data>>> selectRandom(
            @RequestParam(name = "count", defaultValue = "1") int count
    ) {
        List<Data> result = dataService.selectRandom(count);
        int resultCount = result.size();
        String message = String.format("随机查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询页面
    @RequestMapping("/select-page")
    public ResponseEntity<Result<List<Data>>> selectByPage(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit") int limit
    ) {
        List<Data> result = dataService.selectByPage(offset, limit);
        int resultCount = result.size();
        String message = String.format("页面查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 逐项查询
    @RequestMapping("/select-ids")
    public ResponseEntity<Result<List<Data>>> selectByIds(
            @RequestParam(name = "ids") String ids
    ) {
        List<Data> result = dataService.selectByIds(dataService.parseIdsFromString(ids).toArray(new Long[0]));
        int resultCount = result.size();
        String message = String.format("逐项查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 分批查询
    @RequestMapping("/select-ids-batch")
    public ResponseEntity<Result<List<Data>>> selectBatchByIds(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        List<Data> result = dataService.selectBatchByIds(dataService.parseIdsFromString(ids), batchSize);
        int resultCount = result.size();
        String message = String.format("分批查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 逐项插入
    @RequestMapping("/insert")
    public ResponseEntity<Result<Void>> insert(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        int affectedRows = dataService.insert(dataService.generateMockData(total).toArray(new Data[0]));
        String message = String.format("逐项插入 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入
    @RequestMapping("/insert-batch")
    public ResponseEntity<Result<Void>> insertBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.insertBatch(dataService.generateMockData(total), batchSize);
        String message = String.format("分批插入 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项更新
    @RequestMapping("/update")
    public ResponseEntity<Result<Void>> update(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        List<Data> dataList = dataService.parseDataFromJsonFile("script/mock_data.json");
        int affectedRows = dataService.update(dataList.subList(0, Math.min(total, dataList.size())).toArray(new Data[0]));
        String message = String.format("逐项更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批更新
    @RequestMapping("/update-batch")
    public ResponseEntity<Result<Void>> updateBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        List<Data> dataList = dataService.parseDataFromJsonFile("script/mock_data.json");
        int affectedRows = dataService.updateBatch(dataList.subList(0, Math.min(total, dataList.size())), batchSize);
        String message = String.format("分批更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项插入或更新
    @RequestMapping("/upsert")
    public ResponseEntity<Result<Void>> upsert(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        int affectedRows = dataService.upsert(dataService.generateMockData(total).toArray(new Data[0]));
        String message = String.format("逐项插入或更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入或更新
    @RequestMapping("/upsert-batch")
    public ResponseEntity<Result<Void>> upsertBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.upsertBatch(dataService.generateMockData(total), batchSize);
        String message = String.format("分批插入或更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项删除
    @RequestMapping("/delete-ids")
    public ResponseEntity<Result<Void>> deleteById(
            @RequestParam(name = "ids") String ids
    ) {
        int affectedRows = dataService.deleteByIds(dataService.parseIdsFromString(ids).toArray(new Long[0]));
        String message = String.format("逐项删除 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批删除
    @RequestMapping("/delete-ids-batch")
    public ResponseEntity<Result<Void>> deleteBatchByIds(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.deleteBatchByIds(dataService.parseIdsFromString(ids), batchSize);
        String message = String.format("分批删除 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 清空
    @RequestMapping("/truncate")
    public ResponseEntity<Result<Void>> truncate() {
        int count = dataService.countAll();
        dataService.truncate();
        String message = String.format("清空 %d 条数据", count);
        return ResponseEntity.ok().body(Result.success(message, null));
    }
}
