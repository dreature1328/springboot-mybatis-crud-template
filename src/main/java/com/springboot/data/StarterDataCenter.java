package com.springboot.data;

import com.springboot.data.common.pojo.Data;
import com.springboot.data.common.utils.SpringContextUtils;
import com.springboot.data.service.DataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@MapperScan("com.springboot.data.mapper")
@Import(SpringContextUtils.class)
public class StarterDataCenter {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(StarterDataCenter.class, args);

		// 下面是直接在启动类里增删改查的示例
		DataService dataService = SpringContextUtils.getBean(DataService.class);
		Data data1 = new Data("id1","value11","value12");
		Data data2 = new Data("id2","value21","value22");
		Data data3 = new Data("id3","value31","value32");

		Data[] dataArray = {data1, data2, data3};

		List<Data> dataList = new ArrayList<Data>(){{
			add(data1);
			add(data2);
			add(data3);
		}};

		List<String> idList = new ArrayList<String>(){{
			add("id1");
			add("id2");
			add("id3");
		}};

		// 直接调用 Service 层函数
		dataService.insertData(data1);
		dataService.insertData(data1,data2);
		dataService.updateData(dataArray);
		dataService.batchInsertOrUpdateData(dataList);
	}
}
