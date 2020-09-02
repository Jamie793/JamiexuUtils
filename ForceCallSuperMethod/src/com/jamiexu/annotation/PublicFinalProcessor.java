package com.jamiexu.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/2
 * @time 9:34
 * @blog https://blog.jamiexu.cn
 **/




//Java源代码版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//设置注解器注解的类型
@SupportedAnnotationTypes("com.jamiexu.annotation.PublicFinal")
public class PublicFinalProcessor extends AbstractProcessor {


    //记住这里必须是public否则会报错，Error:java: 服务配置文件不正确, 或构造处理程序对象javax.annotation.processing.Processor: Provider
    public PublicFinalProcessor() {
        super();
    }

//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.RELEASE_8;
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        HashSet<String> hashSet = new HashSet<>();
//        hashSet.add(PublicFinal.class.getCanonicalName());
//        return hashSet;
//    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element:roundEnv.getElementsAnnotatedWith(PublicFinal.class)){//找到带有PublicFinal注解的类

            List<? extends Element> elementList = element.getEnclosedElements();
            for (Element e : elementList){

                //如果不是字段的话就重新循环
                if(!e.getKind().isField())
                    continue;
                //判断所有变量是否都带有了public和final修饰符
                if(!e.getModifiers().contains(Modifier.PUBLIC) || !e.getModifiers().contains(Modifier.FINAL)){

                    //如果没有修饰符就报错误
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,"Field is not public and final",e);
                }
            }
        }
        return true;
    }
}
