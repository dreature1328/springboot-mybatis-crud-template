package xyz.dreature.smct.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.dreature.smct.common.util.IdUtils;
import xyz.dreature.smct.common.vo.Result;
import xyz.dreature.smct.service.BaseService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

// 基接口
@Slf4j
@Validated
public abstract class BaseController<T, ID extends Serializable> {
    protected BaseService<T, ID> service;
    protected Function<String, ID> idParser;
    @Value("${app.file.path}")
    protected String filePath;

    public BaseController(BaseService<T, ID> service, Function<String, ID> idParser) {
        this.service = service;
        this.idParser = idParser;
    }

    // ===== 查询基础操作 =====
    // 查询总数
    @RequestMapping("/count-all")
    public ResponseEntity<Result<Integer>> countAll() {
        int count = service.countAll();
        String message = String.format("查询总数为 %d 条", count);
        log.info("查询总数完成，条数：{}", count);
        return ResponseEntity.ok().body(Result.success(message, count));
    }

    // 查询全表
    @RequestMapping("/select-all")
    public ResponseEntity<Result<List<T>>> selectAll() {
        List<T> result = service.selectAll();
        int resultCount = result.size();
        String message = String.format("全表查询 %d 条数据", resultCount);
        log.info("全表查询完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询随机
    @RequestMapping("/select-random")
    public ResponseEntity<Result<List<T>>> selectRandom(
            @RequestParam(name = "count", defaultValue = "1")
            @Positive(message = "条数必须为正")
            int count
    ) {
        List<T> result = service.selectRandom(count);
        int resultCount = result.size();
        String message = String.format("随机查询 %d 条数据", resultCount);
        log.info("随机查询完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 查询页面
    @RequestMapping("/select-by-page")
    public ResponseEntity<Result<List<T>>> selectByPage(
            @RequestParam(name = "offset", defaultValue = "0")
            @Min(value = 0, message = "偏移量不能为负")
            int offset,

            @RequestParam(name = "limit")
            @Min(value = 0, message = "限数不能为负")
            int limit
    ) {
        List<T> result = service.selectByPage(offset, limit);
        int resultCount = result.size();
        String message = String.format("页面查询 %d 条数据", resultCount);
        log.info("页面查询完成，条数:{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 逐项查询
    @RequestMapping("/select-by-ids")
    public ResponseEntity<Result<List<T>>> selectByIds(
            @RequestParam(name = "ids")
            @NotBlank(message = "ID 不能为空")
            @Pattern(regexp = "^\\d+(,\\d+)*$", message = "ID 需由逗号分隔")
            String ids
    ) {
        List<T> result = service.selectByIds(IdUtils.parseIds(ids, idParser));
        int resultCount = result.size();
        String message = String.format("逐项查询 %d 条数据", resultCount);
        log.info("逐项查询完成，条数:{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 分批查询
    @RequestMapping("/select-batch-by-ids")
    public ResponseEntity<Result<List<T>>> selectBatchByIds(
            @RequestParam(name = "ids")
            @NotBlank(message = "ID 不能为空")
            @Pattern(regexp = "^\\d+(,\\d+)*$", message = "ID 需由逗号分隔")
            String ids,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Min(value = 1, message = "批大小至少为 1")
            int batchSize
    ) {
        List<T> result = service.selectBatchByIds(IdUtils.parseIds(ids, idParser), batchSize);
        int resultCount = result.size();
        String message = String.format("分批查询 %d 条数据", resultCount);
        log.info("分批查询完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }


    // ===== 插入基础操作 =====
    // 逐项插入
    @RequestMapping("/insert")
    public ResponseEntity<Result<Void>> insert(
            @RequestParam(name = "total", defaultValue = "10")
            @Min(value = 1, message = "总项数至少为 1")
            int total
    ) {
        int affectedRows = service.insert(service.generateMock(total));
        String message = String.format("逐项插入 %d 条数据", affectedRows);
        log.info("逐项插入完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入
    @RequestMapping("/insert-batch")
    public ResponseEntity<Result<Void>> insertBatch(
            @RequestParam(name = "total", defaultValue = "1000")
            @Min(value = 1, message = "总批数至少为 1")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Min(value = 1, message = "批大小至少为 1")
            int batchSize
    ) {
        int affectedRows = service.insertBatch(service.generateMock(total), batchSize);
        String message = String.format("分批插入 %d 条数据", affectedRows);
        log.info("分批插入完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 更新基础操作 =====
    // 逐项更新
    @RequestMapping("/update")
    public ResponseEntity<Result<Void>> update(
            @RequestParam(name = "total", defaultValue = "10")
            @Min(value = 1, message = "总项数至少为 1")
            int total
    ) {
        List<T> dataList = service.parseFromJsonFile(filePath);
        int affectedRows = service.update(dataList.subList(0, Math.min(total, dataList.size())));
        String message = String.format("逐项更新 %d 条数据", affectedRows);
        log.info("逐项更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批更新
    @RequestMapping("/update-batch")
    public ResponseEntity<Result<Void>> updateBatch(
            @RequestParam(name = "total", defaultValue = "1000")
            @Min(value = 1, message = "总批数至少为 1")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Min(value = 1, message = "批大小至少为 1")
            int batchSize
    ) {
        List<T> dataList = service.parseFromJsonFile(filePath);
        int affectedRows = service.updateBatch(dataList.subList(0, Math.min(total, dataList.size())), batchSize);
        String message = String.format("分批更新 %d 条数据", affectedRows);
        log.info("分批更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 插入或更新基础操作 =====
    // 逐项插入或更新
    @RequestMapping("/upsert")
    public ResponseEntity<Result<Void>> upsert(
            @RequestParam(name = "total", defaultValue = "10")
            @Min(value = 1, message = "总项数至少为 1")
            int total
    ) {
        int affectedRows = service.upsert(service.generateMock(total));
        String message = String.format("逐项插入或更新 %d 条数据", affectedRows);
        log.info("逐项插入或更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批插入或更新
    @RequestMapping("/upsert-batch")
    public ResponseEntity<Result<Void>> upsertBatch(
            @RequestParam(name = "total", defaultValue = "1000")
            @Min(value = 1, message = "总批数至少为 1")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Min(value = 1, message = "批大小至少为 1")
            int batchSize
    ) {
        int affectedRows = service.upsertBatch(service.generateMock(total), batchSize);
        String message = String.format("分批插入或更新 %d 条数据", affectedRows);
        log.info("分批插入或更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 删除基础操作 =====
    // 逐项删除
    @RequestMapping("/delete-by-ids")
    public ResponseEntity<Result<Void>> deleteById(
            @RequestParam(name = "ids") String ids
    ) {
        int affectedRows = service.deleteByIds(IdUtils.parseIds(ids, idParser));
        String message = String.format("分批更新 %d 条数据", affectedRows);
        log.info("分批更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // 分批删除
    @RequestMapping("/delete-batch-by-ids")
    public ResponseEntity<Result<Void>> deleteBatchByIds(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Min(value = 1, message = "批大小至少为 1")
            int batchSize
    ) {
        int affectedRows = service.deleteBatchByIds(IdUtils.parseIds(ids, idParser), batchSize);
        String message = String.format("逐项插入或更新 %d 条数据", affectedRows);
        log.info("逐项插入或更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 其他操作 =====
    // 清空
    @RequestMapping("/truncate")
    public ResponseEntity<Result<Void>> truncate() {
        int count = service.countAll();
        service.truncate();
        String message = String.format("清空 %d 条数据", count);
        log.info("清空完成，条数：{}", count);
        return ResponseEntity.ok().body(Result.success(message, null));
    }
}
