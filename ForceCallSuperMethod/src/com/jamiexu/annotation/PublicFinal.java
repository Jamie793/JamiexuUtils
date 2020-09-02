package com.jamiexu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/2
 * @time 9:28
 * @blog https://blog.jamiexu.cn
 **/

@Target(ElementType.TYPE) //注解类型
@Retention(RetentionPolicy.SOURCE)//注解环境
public @interface PublicFinal {
}
