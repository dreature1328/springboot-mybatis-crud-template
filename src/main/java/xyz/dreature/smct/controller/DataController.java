package xyz.dreature.smct.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.common.vo.Result;
import xyz.dreature.smct.controller.base.BaseController;
import xyz.dreature.smct.service.DataService;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Validated
@RestController
@RequestMapping("/data")
@Tag(name = "数据操作")
public class DataController extends BaseController<Data, Long, DataService> {
    private final DataService dataService;

    @Value("${app.file.path}")
    protected String filePath;

    @Autowired
    public DataController(DataService dataService) {
        super(dataService);
        this.dataService = dataService;
    }

    // ===== 业务扩展操作 =====
    @Operation(summary = "处理游标数据")
    @PostMapping("/process-all")
    public ResponseEntity<Result<Void>> processAllWithCursor() {
        int count = service.countAll();
        // 根据需要自定义处理函数
        Consumer<Data> consumer = data -> {
            log.info("{}", data.getId());
        };
        dataService.processAllWithCursor(consumer);
        String message = String.format("处理 %d 条数据", count);
        log.info("数据处理完成，条数：{}", count);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    @Operation(summary = "生成模拟数据")
    @PostMapping("/generate-mock")
    public ResponseEntity<Result<List<Data>>> generateMock(
            @RequestParam(name = "count", defaultValue = "10")
            @Positive(message = "数量必须为正")
            int count
    ) {
        List<Data> result = dataService.generateMock(count);
        int resultCount = result.size();
        String message = String.format("生成 %d 条数据", resultCount);
        log.info("数据生成完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    @Operation(summary = "解析 JSON 数据")
    @PostMapping("/parse-json")
    public ResponseEntity<Result<List<Data>>> parseJsonFile() {
        List<Data> result = dataService.parseJsonFile(filePath);
        int resultCount = result.size();
        String message = String.format("解析 %d 条数据", resultCount);
        log.info("数据解析完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // ===== 插入模拟操作 =====
    @Operation(summary = "模拟逐项插入")
    @PostMapping("/insert-mock")
    public ResponseEntity<Result<Void>> insertMock(
            @RequestParam(name = "total", defaultValue = "10")
            @Positive(message = "总项数必须为正")
            int total
    ) {
        List<Data> entityList = service.generateMock(total);

        int affectedRows = service.insert(entityList);
        String message = String.format("逐项插入 %d 条数据", affectedRows);
        log.info("逐项插入完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    @Operation(summary = "模拟分批插入")
    @PostMapping("/insert-batch-mock")
    public ResponseEntity<Result<Void>> insertBatchMock(
            @RequestParam(name = "total", defaultValue = "1000")
            @Positive(message = "总批数必须为正")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Positive(message = "批大小必须为正")
            int batchSize
    ) {
        List<Data> entityList = service.generateMock(total);

        int affectedRows = service.insertBatch(entityList, batchSize);
        String message = String.format("分批插入 %d 条数据", affectedRows);
        log.info("分批插入完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 更新模拟操作 =====
    @Operation(summary = "模拟逐项更新")
    @PostMapping("/update-mock")
    public ResponseEntity<Result<Void>> updateMock(
            @RequestParam(name = "total", defaultValue = "10")
            @Positive(message = "总项数必须为正")
            int total
    ) {
        List<Data> entityList = service.parseJsonFile(filePath);
        entityList = entityList.subList(0, Math.min(total, entityList.size()));

        int affectedRows = service.update(entityList);
        String message = String.format("逐项更新 %d 条数据", affectedRows);
        log.info("逐项更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    @Operation(summary = "模拟分批更新")
    @PostMapping("/update-batch-mock")
    public ResponseEntity<Result<Void>> updateBatchMock(
            @RequestParam(name = "total", defaultValue = "1000")
            @Positive(message = "总批数必须为正")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Positive(message = "批大小必须为正")
            int batchSize
    ) {
        List<Data> entityList = service.parseJsonFile(filePath);
        entityList = entityList.subList(0, Math.min(total, entityList.size()));

        int affectedRows = service.updateBatch(entityList, batchSize);
        String message = String.format("分批更新 %d 条数据", affectedRows);
        log.info("分批更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    // ===== 插入或更新模拟操作 =====
    @Operation(summary = "模拟逐项插入或更新")
    @PostMapping("/upsert-mock")
    public ResponseEntity<Result<Void>> upsertMock(
            @RequestParam(name = "total", defaultValue = "10")
            @Positive(message = "总项数必须为正")
            int total
    ) {
        List<Data> entityList = service.generateMock(total);

        int affectedRows = service.upsert(entityList);
        String message = String.format("逐项插入或更新 %d 条数据", affectedRows);
        log.info("逐项插入或更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }

    @Operation(summary = "模拟分批插入或更新")
    @PostMapping("/upsert-batch-mock")
    public ResponseEntity<Result<Void>> upsertBatchMock(
            @RequestParam(name = "total", defaultValue = "1000")
            @Positive(message = "总批数必须为正")
            int total,

            @RequestParam(name = "batch-size", defaultValue = "1000")
            @Positive(message = "批大小必须为正")
            int batchSize
    ) {
        List<Data> entityList = service.generateMock(total);

        int affectedRows = service.upsertBatch(entityList, batchSize);
        String message = String.format("分批插入或更新 %d 条数据", affectedRows);
        log.info("分批插入或更新完成，影响行数：{}", affectedRows);
        return ResponseEntity.ok().body(Result.success(message, null));
    }
}
