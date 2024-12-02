package com.lightcs.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.lightcs.common.enums.UserRoleEnum.ADMIN;

/**
 * @Author: peak-like
 * @Description: sa-token配置类
 * @DateTime: 2024/12/2 16:23
 **/
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            SaRouter.match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/user/login")        // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin())        // 要执行的校验动作，可以写完整的 lambda 表达式
                    .match("/admin/**", r -> StpUtil.checkRole(ADMIN.getValue()));
        })).addPathPatterns("/**");
    }
}

