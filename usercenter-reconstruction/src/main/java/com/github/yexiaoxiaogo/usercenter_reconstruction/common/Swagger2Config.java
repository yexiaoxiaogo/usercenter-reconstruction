package com.github.yexiaoxiaogo.usercenter_reconstruction.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {


	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
//	 @Bean
//	    public Docket createRestApi() {
//	        return new Docket(DocumentationType.SWAGGER_2)
//	                .apiInfo(apiInfo())
//	                .select()
//	                // 指定controller存放的目录路径
//	                .apis(RequestHandlerSelectors.basePackage("com.digag.web"))
//	                .paths(PathSelectors.any())
//	                .build();
//	    }
//
//	    private ApiInfo apiInfo() {
//	        return new ApiInfoBuilder()
//	                 // 文档标题
//	                .title("usercenter")
//	                // 文档描述
//	                .description("用户中心项目接口文档")
//	                .termsOfServiceUrl("http://47.97.184.82:23250")
//	                .version("v1")
//	                .build();
//	    }
}
