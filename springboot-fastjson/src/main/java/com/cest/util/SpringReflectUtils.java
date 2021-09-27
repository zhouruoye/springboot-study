package com.cest.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpringReflectUtils implements ApplicationContextAware {
    /**
     * Spring容器 spring应用上下文对象
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringReflectUtils.applicationContext = applicationContext;
    }

    /**
     * 对象名称获取spring bean对象
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 根据 服务名称 ，方法名 反射调用  spring bean 中的 方法
     * @param serviceName 服务名
     * @param methodName 方法名
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static <T> List<T> springInvokeMethod(String serviceName, String methodName, Object[] params,Class<T> tClass) throws Exception {
        Object service = getBean(serviceName);
        Class<? extends Object>[] paramClass = null;
        Method method = method = ReflectionUtils.findMethod(service.getClass(), methodName, null);
        Object o = null;
        List<T> all = new ArrayList<>();
        if (params != null) {
            List<String> param = ((List<String>)params[0]).stream().collect(Collectors.toList());
            int oneCount = 2;
            int size = param.size();
            int repeatCount = size / oneCount + (size % oneCount > 0 ? 1 : 0);

            for (int i = 0; i < repeatCount; i++) {
                List<String> collect = param.stream().skip(i * oneCount).limit(oneCount).collect(Collectors.toList());
                params[0] = collect;
                o = ReflectionUtils.invokeMethod(method, service, params);
                List<T> o1 = (List<T>) o;
                all.addAll(o1);
            }
        }
        return all;
    }

}
