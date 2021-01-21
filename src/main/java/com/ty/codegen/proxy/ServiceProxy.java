package com.ty.codegen.proxy;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @Project: codegen
 * @ClassName: ServiceProxy
 * @Author: ty
 * @Description: 业务代理对象
 * @Date: 2021/1/16
 * @Version: 1.0
 **/
public class ServiceProxy {

    /**
     * 要增强的类必须要有接口
     * 将实现类进行增强
     * @param targetObject
     * @param <T>
     * @return
     */
    public static <T> T proxyPowerful(T targetObject) {
        if (Objects.isNull(targetObject)) {

        }
        Class<?> objectClass = targetObject.getClass();
        Object powerfulObject = Proxy.newProxyInstance(objectClass.getClassLoader(), objectClass
                .getInterfaces(), (proxy, method, args) -> {
            Object invoke = null;
            try {
                invoke = method.invoke(targetObject, args);
            } catch (Exception e) {
                System.out.println("=======全局异常处理===>" + e.getMessage());
                e.printStackTrace();
            }
            return invoke;
        });
        return (T) powerfulObject;
    }
}
