package com.springboot.data.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DataController {
	@Autowired
	private DataService dataService;
	// 发送请求
	@RequestMapping("/data/request")
	public String requestData(HttpServletRequest request) throws Exception {
		return dataService.requestData(request.getParameterMap());
	}

	// 依次查询
	@RequestMapping("/data/select")
	public JSONObject selectData(String id) throws Exception {
		return (JSONObject) JSON.toJSON(dataService.selectData(id));
	}

	// 批量查询
	@RequestMapping("/data/bselect")
	public JSONArray batchSelectData(String ids) throws Exception {
		return JSONArray.parseArray(JSON.toJSONString(dataService.batchSelectData(ids)));
	}

	// 分页查询
	@RequestMapping("/data/pselect")
	public JSONArray pageSelectData(String ids) throws Exception {
		return JSONArray.parseArray(JSON.toJSONString(dataService.pageSelectData(ids)));
	}

	// 依次插入
	@RequestMapping("/data/insert")
	public void insertData() throws Exception {
		dataService.insertData();
		return ;
	}

	// 批量插入
	@RequestMapping("/data/binsert")
	public void batchInsertData() throws Exception {
		dataService.batchInsertData();
		return ;
	}

	// 分页插入
	@RequestMapping("/data/pinsert")
	public void pageInsertData() throws Exception {
		dataService.pageInsertData();
		return ;
	}

	// 依次更新
	@RequestMapping("/data/update")
	public void updateData() throws Exception {
		dataService.updateData();
		return ;
	}

	// 批量更新
	@RequestMapping("/data/bupdate")
	public void batchUpdateData() throws Exception {
		dataService.batchUpdateData();
		return ;
	}

	// 分页更新
	@RequestMapping("/data/pupdate")
	public void pageUpdateData() throws Exception {
		dataService.pageUpdateData();
		return ;
	}

	// 依次插入或更新
	@RequestMapping("/data/insertorupdate")
	public void insertOrUpdateData() throws Exception {
		dataService.insertOrUpdateData();
		return ;
	}

	// 批量插入或更新
	@RequestMapping("/data/binsertorupdate")
	public void batchInsertOrUpdateData() throws Exception {
		dataService.batchInsertOrUpdateData();
		return ;
	}

	// 分页插入或更新
	@RequestMapping("/data/pinsertorupdate")
	public void pageInsertOrUpdateData() throws Exception {
		dataService.pageInsertOrUpdateData();
		return ;
	}

	// 依次删除
	@RequestMapping("/data/delete")
	public void deleteData(String id) throws Exception {
		dataService.deleteData(id);
		return ;
	}

	// 批量删除
	@RequestMapping("/data/bdelete")
	public void batchDeleteData(String ids) throws Exception {
		dataService.batchDeleteData(ids);
		return ;
	}

	// 分页删除
	@RequestMapping("/data/pdelete")
	public void pageDeleteData(String ids) throws Exception {
		dataService.pageDeleteData(ids);
		return ;
	}

	// 清空
	@RequestMapping("/data/clear")
	public void clearData() throws Exception {
		dataService.clearData();
		return ;
	}
}
