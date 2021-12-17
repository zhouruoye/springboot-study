package com.cest.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;

public class ObjectFactory {


    public static <T> T newInstance2(Class<T> cls) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 记录方法与相关处理的映射之间的关系
        HashMap<String,LinkedList<Aspect> > methodAspectMap = new HashMap<>();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Aspect.class)) {
                Class[] types = method.getAnnotation(Aspect.class).types();
                LinkedList<Aspect> aspects = new LinkedList<>();
                for (Class type : types) {
                    Aspect aspect = (Aspect) type.getConstructor().newInstance();
                    aspects.push(aspect);
                }
                methodAspectMap.put(method.getName(), aspects);
            }
        }

        return null;

//        T target = cls.getConstructor().newInstance();
//        return (T) Proxy.newProxyInstance(
//                cls.getClassLoader(),
//                cls.getInterfaces(),
//                (proxy, method, args) -> {
//                    // 根据方法名，执行对应注解的该干的事
//                    if (methodAspectMap.containsKey(method.getName())) {
//                        LinkedList<Aspect> aspects = methodAspectMap.get(method.getName());
//                        for (Aspect aspect : aspects) {
//                            aspect.before();
//                        }
//                        Object result = method.invoke(target, args);
//                        for (Aspect aspect : aspects) {
//                            aspect.after();
//                        }
//                        return result;
//                    }
//                    return method.invoke(target, args);
//                }
//        );
    }
}
