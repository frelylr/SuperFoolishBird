package org.frelylr.sfb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dingcc
 * @Title: SwaggerConfiguration
 * @ProjectName HotLineSystem
 * @Description: swagger ui的配置类，用来做接口说明
 * @date 2018/11/1910:35
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swaggerUI.show:false}")
    private boolean swaggerShow;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("org.frelylr.sfb.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("前后台交互调试页")
                .description("前后台交互调试页")
                .termsOfServiceUrl("http://localhost:8088/")
                .contact("developer@mail.com")
                .version("1.0")
                .build();
    }


}
