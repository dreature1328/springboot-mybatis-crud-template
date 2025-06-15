package dreature.smct.controller;

import dreature.smct.common.vo.Result;
import dreature.smct.entity.Data;
import dreature.smct.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 测试接口
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    // 生成数据（测试用）
    @RequestMapping("/generate")
    public ResponseEntity<Result> generate(
            @RequestParam(name = "count", defaultValue = "10") int count
    ) {
        List<Data> result = dataService.generate(count);
        int resultCount = result.size();
        String message = String.format("生成 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 解析数据（测试用）
    @RequestMapping("/parse")
    public ResponseEntity<Result> parse() {
        List<Data> result = dataService.parse("script/mock_data.json");
        int resultCount = result.size();
        String message = String.format("解析 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询总数
    @RequestMapping("/count")
    public ResponseEntity<Result> countAll() {
        int count = dataService.countAll();
        String message = String.format("查询总数为 %d 条", count);
        return ResponseEntity.ok().body(Result.success(message, count));
    }

    // 查询数据
    @RequestMapping("/find")
    public ResponseEntity<Result> find(
            @RequestParam(name = "count", required = false) Integer count
    ) {
        List<Data> result = (count == null ? dataService.findAll() : dataService.findRandomN(count));
        int resultCount = result.size();
        String message = String.format("查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 逐项查询
    @RequestMapping("/select")
    public ResponseEntity<Result> selectByIds(
            @RequestParam(name = "ids") String ids
    ) {
        List<Data> result = dataService.selectByIds(ids.split(","));
        int resultCount = result.size();
        String message = String.format("逐项查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 分批查询
    @RequestMapping("/select-batch")
    public ResponseEntity<Result> selectBatchByIds(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        List<Data> result = dataService.selectBatchByIds(new ArrayList<>(Arrays.asList(ids.split(","))), batchSize);
        int resultCount = result.size();
        String message = String.format("分批查询 %d 条数据", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 逐项插入
    @RequestMapping("/insert")
    public ResponseEntity<Result> insert(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        int affectedRows = dataService.insert(dataService.generate(total).toArray(new Data[0]));
        String message = String.format("逐项插入 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入
    @RequestMapping("/insert-batch")
    public ResponseEntity<Result> insertBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.insertBatch(dataService.generate(total), batchSize);
        String message = String.format("分批插入 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项更新
    @RequestMapping("/update")
    public ResponseEntity<Result> update(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        int affectedRows = dataService.update(dataService.generate(total).toArray(new Data[0]));
        String message = String.format("逐项更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批更新
    @RequestMapping("/update-batch")
    public ResponseEntity<Result> updateBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.updateBatch(dataService.generate(total), batchSize);
        String message = String.format("分批更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项插入或更新
    @RequestMapping("/upsert")
    public ResponseEntity<Result> upsert(
            @RequestParam(name = "total", defaultValue = "10") int total
    ) {
        int affectedRows = dataService.upsert(dataService.generate(total).toArray(new Data[0]));
        String message = String.format("逐项插入或更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入或更新
    @RequestMapping("/upsert-batch")
    public ResponseEntity<Result> upsertBatch(
            @RequestParam(name = "total", defaultValue = "1000") int total,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.upsertBatch(dataService.generate(total), batchSize);
        String message = String.format("分批插入或更新 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 逐项删除
    @RequestMapping("/delete")
    public ResponseEntity<Result> deleteById(
            @RequestParam(name = "ids") String ids
    ) {
        int affectedRows = dataService.deleteByIds(ids.split(","));
        String message = String.format("逐项删除 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批删除
    @RequestMapping("/delete-batch")
    public ResponseEntity<Result> deleteBatchByIds(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "batch-size", defaultValue = "1000") int batchSize
    ) {
        int affectedRows = dataService.deleteBatchByIds(new ArrayList<>(Arrays.asList(ids.split(","))), batchSize);
        String message = String.format("分批删除 %d 条数据", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 清空
    @RequestMapping("/truncate")
    public ResponseEntity<Result> truncate() {
        int count = dataService.countAll();
        dataService.truncate();
        String message = String.format("清空 %d 条数据", count);
        return ResponseEntity.ok().body(Result.success(message, null));
    }
}
