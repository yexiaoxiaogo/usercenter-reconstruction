package com.github.yexiaoxiaogo.usercenter_reconstruction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hervoo
 */

@SpringBootApplication
@ComponentScan("com.xiaokong")
@MapperScan("com.xiaokong.usercenter.**.mapper")
@AutoConfigureBefore()
@EnableDiscoveryClient
@EnableFeignClients
public class UserCenterServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterServerApplication.class, args);
	}
}
