package com.jamiexu.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static Constructor getConstructor(Class<?> clas, Class<?>... objs) {
        try {
            return clas.getDeclaredConstructor(objs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Constructor getConstructor(Class<?> clas) {
        try {
            return clas.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Constructor[] getAllConstructors(Class<?> clas) {
        return clas.getDeclaredConstructors();
    }


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


    public static Object newInstance(Constructor constructor) {
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

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


    public static Method getMethod(Class<?> clas, String name, Class<?>... objects) {
        try {
            return clas.getDeclaredMethod(name, objects);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clas, String name) {
        try {
            return clas.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Method[] getAllMethods(Class<?> clas) {
        return clas.getDeclaredMethods();
    }


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


    public static Field getField(Class<?> clas, String name) {
        try {
            return clas.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Field[] getAllFields(Class<?> clas) {
        return clas.getDeclaredFields();
    }

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


    public static Object getFieldValue(Field field, Object object, String name) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Field field, Object obj, String name, Object object) {
        try {
            field.setAccessible(true);
            field.set(obj, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Object getStaticFieldValue(Field field, String name) {
        try {
            field.setAccessible(true);
            return field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setStaticFieldValue(Field field, String name, Object object) {
        try {
            field.setAccessible(true);
            field.set(null, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    //    @SuppressLint("NewApi")
    public static Annotation getAnnotation(Class<?> clas, Class type) {
        return clas.getDeclaredAnnotation(type);
    }

    public static Annotation[] getAllAnnotations(Class<?> clas) {
        return clas.getDeclaredAnnotations();
    }


}
