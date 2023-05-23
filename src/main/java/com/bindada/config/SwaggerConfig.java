package com.bindada.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


/**
 * @Author: bindada
 * @date: 2022/9/17
 * @Description: Swagger3配置文件
*/
@SpringBootConfiguration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * ture 启用Swagger3.0， false 禁用（生产环境要禁用）
     */
    Boolean swaggerEnabled=true;
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径使用@Api的controller
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(Collections.singletonList(new springfox.documentation.builders.RequestParameterBuilder()
                        .name("token")
                        .description("token")
                        .in(ParameterType.HEADER)
                        .required(false)
                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                        .build()
                ));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("基于产学研数据协同的智慧课程教学系统")
                .version("1.0")
                .build();
    }
}

