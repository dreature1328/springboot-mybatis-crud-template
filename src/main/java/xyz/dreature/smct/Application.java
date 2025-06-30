package xyz.dreature.smct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import xyz.dreature.smct.common.util.SpringContextUtils;


@SpringBootApplication
@MapperScan("xyz.dreature.smct.mapper")
@Import(SpringContextUtils.class)
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
