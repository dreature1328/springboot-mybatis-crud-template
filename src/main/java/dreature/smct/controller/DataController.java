package dreature.smct.controller;

import dreature.smct.common.vo.Result;
import dreature.smct.entity.Data;
import dreature.smct.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

// 测试接口
@RestController
public class DataController {
	@Autowired
	private DataService dataService;
	
	// 生成对象
	@RequestMapping("/data/generate")
	public ResponseEntity<Result> generate() {
		return ResponseEntity.ok().body(Result.success(dataService.generate(10)));
	}

	// 读取对象
	@RequestMapping("/data/read")
	public ResponseEntity<Result> read() {
		return ResponseEntity.ok().body(Result.success(dataService.read("script/mock_data.json")));
	}

	// 查询总数
	@RequestMapping("/data/count")
	public ResponseEntity<Result> countAll() {
		int count = dataService.countAll();
		String message = String.format("查询到 %d 条数据", count);
		return ResponseEntity.ok().body(Result.success(message, count));
	}

	// 查询 n 条
	@RequestMapping("/data/find")
	public ResponseEntity<Result> findTopN(int n) {
		List<Data> result = dataService.findTopN(n);
		int count = result.size();
		String message = String.format("查询到 %d 条数据", count);
		return ResponseEntity.ok().body(Result.success(message, result));
	}

	// 依次查询
	@RequestMapping("/data/select")
	public ResponseEntity<Result> selectById(String id) {
		List<Data> result = dataService.selectById(id);
		int count = result.size();
		String message = String.format("查询到 %d 条数据", count);
		return ResponseEntity.ok().body(Result.success(message, result));
	}

	// 批量查询
	@RequestMapping("/data/select-b")
	public ResponseEntity<Result> selectBatchByIds(String ids) {
		List<Data> result = dataService.selectBatchByIds(Arrays.asList(ids.split(",")));
		int count = result.size();
		String message = String.format("查询到 %d 条数据", count);
		return ResponseEntity.ok().body(Result.success(message, result));
	}

	// 分页查询
	@RequestMapping("/data/select-p")
	public ResponseEntity<Result> selectPage(String ids) {
		return ResponseEntity.ok().body(Result.success(dataService.selectPageByIds(Arrays.asList(ids.split(",")))));
	}

	// 依次插入
	@RequestMapping("/data/insert")
	public ResponseEntity<Result> insert() {
		int affectedRows = dataService.insert(dataService.generate(10).toArray(new Data[0]));
		String message = String.format("依次插入 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 批量插入
	@RequestMapping("/data/insert-b")
	public ResponseEntity<Result> insertBatch() {
		int affectedRows = dataService.insertBatch(dataService.generate(1000));
		String message = String.format("批量插入 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 分页插入
	@RequestMapping("/data/insert-p")
	public ResponseEntity<Result> insertPage() {
		dataService.insertPage(dataService.generate(100000));
		return ResponseEntity.ok().body(Result.success(null));
	}

	// 依次更新
	@RequestMapping("/data/update")
	public ResponseEntity<Result> update() {
		int affectedRows = dataService.update(dataService.generate(10).toArray(new Data[0]));
		String message = String.format("依次更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 批量更新
	@RequestMapping("/data/update-b")
	public ResponseEntity<Result> updateBatch() {
		int affectedRows = dataService.updateBatch(dataService.generate(1000));
		String message = String.format("批量更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 分页更新
	@RequestMapping("/data/update-p")
	public ResponseEntity<Result> updatePage() {
		int affectedRows = dataService.updatePage(dataService.generate(100000));
		String message = String.format("分页更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 依次插入或更新
	@RequestMapping("/data/upsert")
	public ResponseEntity<Result> upsert() {
		int affectedRows = dataService.upsert(dataService.generate(10).toArray(new Data[0]));
		String message = String.format("插入或更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));

	}

	// 批量插入或更新
	@RequestMapping("/data/upsert-b")
	public ResponseEntity<Result> upsertBatch() {
		int affectedRows = dataService.upsertBatch(dataService.generate(1000));
		String message = String.format("批量插入或更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 分页插入或更新
	@RequestMapping("/data/upsert-p")
	public ResponseEntity<Result> upsertPage() {
		int affectedRows = dataService.upsertPage(dataService.generate(100000));
		String message = String.format("分页插入或更新 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 依次删除
	@RequestMapping("/data/delete")
	public ResponseEntity<Result> deleteById(String id) {
		int affectedRows = dataService.deleteById(id);
		String message = String.format("删除 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 批量删除
	@RequestMapping("/data/delete-b")
	public ResponseEntity<Result> deleteBatchByIds(String ids) {
		int affectedRows = dataService.deleteBatchByIds(Arrays.asList(ids.split(",")));
		String message = String.format("批量删除 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 分页删除
	@RequestMapping("/data/delete-p")
	public ResponseEntity<Result> deletePageByIds(String ids) {
		int affectedRows = dataService.deletePageByIds(Arrays.asList(ids.split(",")));
		String message = String.format("分页删除 %d 条数据", affectedRows);
		return ResponseEntity.ok().body(Result.success(message, null));
	}

	// 清空
	@RequestMapping("/data/truncate")
	public ResponseEntity<Result> truncate() {
		int count = dataService.countAll();
		dataService.truncate();
		String message = String.format("清空 %d 条数据", count);
		return ResponseEntity.ok().body(Result.success(message, null));
	}
}
