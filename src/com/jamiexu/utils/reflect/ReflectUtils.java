package com.jamiexu.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class ReflectUtils {

    /**
     * 获取Class构造器
     *
     * @param clas 需要获取构造器的Class类
     * @param objs 构造器参数class
     * @return Constructor 构造器
     */
    public static Constructor getConstructor(Class<?> clas, Class<?>... objs) {
        try {
            return clas.getDeclaredConstructor(objs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取默认构造其
     *
     * @param clas 需要获取构造器的Class类
     * @return Constructor 构造器
     */
    public static Constructor getConstructor(Class<?> clas) {
        try {
            return clas.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Class所有构造器
     *
     * @param clas 需要获取构造器的Class类
     * @return Constructor[] 构造器数组
     */
    public static Constructor[] getAllConstructors(Class<?> clas) {
        return clas.getDeclaredConstructors();
    }

    /**
     * 实现接口
     *
     * @param constructor 构造器
     * @param objects     参数
     * @return Object 对应的Class接口
     */
    public static Object newInstance(Constructor constructor, Object... objects) {
        try {
            return constructor.newInstance(objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实现接口
     *
     * @param clas    需要实现接口的Class类
     * @param objects 参数
     * @return Object 对应的Class接口
     */
    public static Object newInstance(Class<?> clas, Object... objects) {
        try {
            Constructor constructor = getConstructor(clas);
            if (constructor == null)
                return null;
            constructor.setAccessible(true);
            return constructor.newInstance(objects);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实现接口
     *
     * @param constructor 构造器
     * @return Object 对应的Class接口
     */
    public static Object newInstance(Constructor constructor) {
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实现接口
     *
     * @param clas 需要实现接口的Class类
     * @return Object 对应的Class接口
     */
    public static Object newInstance(Class<?> clas) {
        try {
            Constructor constructor = getConstructor(clas);
            if (constructor == null)
                return null;
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param clas    需要获取的class
     * @param name    方法名称
     * @param objects 方法参数
     * @return Method 方法
     */
    public static Method getMethod(Class<?> clas, String name, Class<?>... objects) {
        try {
            return clas.getDeclaredMethod(name, objects);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param clas 需要获取的lass
     * @param name 方法名称
     * @return Method 方法
     */
    public static Method getMethod(Class<?> clas, String name) {
        try {
            return clas.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有方法
     *
     * @param clas 需要获取的class
     * @return Method[] 方法数组
     */
    public static Method[] getAllMethods(Class<?> clas) {
        return clas.getDeclaredMethods();
    }

    /**
     * 调用方法
     *
     * @param clas    class类
     * @param object  Context
     * @param name    方法名
     * @param params  方法参数Class
     * @param objects 参数
     * @return Object 调用结果
     */
    public static Object invokeMethod(Class<?> clas, Object object, String name, Class<?> params, Object... objects) {
        try {
            Method method = getMethod(clas, name, params);
            if (method == null)
                return null;
            method.setAccessible(true);
            return method.invoke(object, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用方法
     *
     * @param method 方法
     * @return Object 返回结果
     */
    public static Object invokeMethod(Method method) {
        try {
            method.setAccessible(true);
            return method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用方法
     *
     * @param method  方法
     * @param objects 参数
     * @return Object 返回结果
     */
    public static Object invokeMethod(Method method, Object... objects) {
        try {
            method.setAccessible(true);
            return method.invoke(null, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用方法
     *
     * @param clas   class
     * @param object context
     * @param name   方法名
     * @return Object 返回结果
     */
    public static Object invokeMethod(Class<?> clas, Object object, String name) {
        try {
            Method method = getMethod(clas, name);
            if (method == null)
                return null;
            method.setAccessible(true);
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用静态方法
     *
     * @param clas    被调用方法的class
     * @param name    方法的名称
     * @param params  方法需要传入的参数
     * @param objects 传入的参数
     * @return Object 调用结果
     */
    public static Object invokeStaticMethod(Class<?> clas, String name, Class<?> params, Object... objects) {
        try {
            Method method = getMethod(clas, name, params);
            if (method == null)
                return null;
            method.setAccessible(true);
            return method.invoke(null, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用静态方法
     *
     * @param clas 被调用方法的class
     * @param name 方法名称
     * @return Object 调用结果
     */
    public static Object invokeStaticMethod(Class<?> clas, String name) {
        try {
            Method method = getMethod(clas, name);
            if (method == null)
                return null;
            method.setAccessible(true);
            return method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用静态方法
     *
     * @param method  被调用的方法
     * @param objects 传入的参数
     * @return Object 调用结果
     */
    public static Object invokeStaticMethod(Method method, Object... objects) {
        try {
            method.setAccessible(true);
            return method.invoke(null, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用静态方法
     *
     * @param method 被调用的方法
     * @return Object 调用结果
     */
    public static Object invokeStaticMethod(Method method) {
        try {
            method.setAccessible(true);
            return method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段
     *
     * @param clas 被获取字段的class
     * @param name 字段名
     * @return Field 字段
     */
    public static Field getField(Class<?> clas, String name) {
        try {
            return clas.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有字段
     *
     * @param clas 被获取字段的class
     * @return Field[] 字段数组
     */
    public static Field[] getAllFields(Class<?> clas) {
        return clas.getDeclaredFields();
    }

    /**
     * 获取字段值
     *
     * @param clas   被获取字段的class
     * @param object 上下文
     * @param name   字段名
     * @return Object 获取结果
     */
    public static Object getField(Class<?> clas, Object object, String name) {
        try {
            Field field = getField(clas, name);
            if (field == null)
                return null;
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param clas   被获取字段的class
     * @param obj    上下文
     * @param name   字段名
     * @param object 需要设置的值
     */
    public static void setField(Class<?> clas, Object obj, String name, Object object) {
        try {
            Field field = getField(clas, name);
            if (field == null)
                return;
            field.setAccessible(true);
            field.set(obj, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取静态字段值
     *
     * @param clas 需要获取字段的class
     * @param name 字段名
     * @return Object 字段值
     */
    public static Object getStaticFieldValue(Class<?> clas, String name) {
        try {
            Field field = getField(clas, name);
            if (field == null)
                return null;
            field.setAccessible(true);
            return field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置静态字段值
     *
     * @param clas   需要设置字段值的class
     * @param name   字段名
     * @param object 字段值
     */
    public static void setStaticFieldValue(Class<?> clas, String name, Object object) {
        try {
            Field field = getField(clas, name);
            if (field == null)
                return;
            field.setAccessible(true);
            field.set(null, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字段值
     *
     * @param field  字段
     * @param object 上下文
     * @param name   字段名
     * @return Object 字段值
     */
    public static Object getFieldValue(Field field, Object object, String name) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param field  字段
     * @param obj    上下文
     * @param name   字段名
     * @param object 字段值
     */
    public static void setFieldValue(Field field, Object obj, String name, Object object) {
        try {
            field.setAccessible(true);
            field.set(obj, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取静态字段值
     *
     * @param field 字段
     * @param name  字段名
     * @return Object 字段值
     */
    public static Object getStaticFieldValue(Field field, String name) {
        try {
            field.setAccessible(true);
            return field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置静态字段值
     *
     * @param field  字段
     * @param name   字段名
     * @param object 字段值
     */
    public static void setStaticFieldValue(Field field, String name, Object object) {
        try {
            field.setAccessible(true);
            field.set(null, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取注解
     *
     * @param clas 需要获取注解的class
     * @param type 注解的参数类型
     * @return Annotation 注解
     */
    public static Annotation getAnnotation(Class<?> clas, Class type) {
        return clas.getDeclaredAnnotation(type);
    }

    /**
     * 获取所有注解
     *
     * @param clas 需要获取注解的class
     * @return Annotation[] 注解数组
     */
    public static Annotation[] getAllAnnotations(Class<?> clas) {
        return clas.getDeclaredAnnotations();
    }


}
