package cn.houyuehui.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jsonhou
 * @Date: 2019/10/31 8:10
 */
@Configuration
@MapperScan(value = {"cn.houyuehui.mapper"})
public class MyBatisConfig {
}
