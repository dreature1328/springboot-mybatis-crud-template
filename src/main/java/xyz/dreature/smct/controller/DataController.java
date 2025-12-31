package xyz.dreature.smct.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.smct.common.entity.Data;
import xyz.dreature.smct.common.vo.Result;
import xyz.dreature.smct.service.DataService;

import javax.validation.constraints.Min;
import java.util.List;

// 测试接口（实体数据）
@Slf4j
@RestController
@RequestMapping("/data")
@Validated
public class DataController extends BaseController<Data, Long> {
    @Autowired
    public DataController(DataService dataService) {
        super(dataService, Long::parseLong);
    }

    // ===== 业务扩展操作 =====
    // 生成模拟数据
    @RequestMapping("/generate-mock")
    public ResponseEntity<Result<List<Data>>> generateMock(
            @RequestParam(name = "count", defaultValue = "10")
            @Min(value = 1, message = "生成数量至少为 1") int count
    ) {
        List<Data> result = service.generateMock(count);
        int resultCount = result.size();
        String message = String.format("生成 %d 条数据", resultCount);
        log.info("数据生成完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }

    // 解析 JSON 数据
    @RequestMapping("/parse-json")
    public ResponseEntity<Result<List<Data>>> parseFromJsonFile() {
        List<Data> result = service.parseFromJsonFile(filePath);
        int resultCount = result.size();
        String message = String.format("解析 %d 条数据", resultCount);
        log.info("数据解析完成，条数：{}", resultCount);
        return ResponseEntity.ok().body(Result.success(message, result));
    }
}
