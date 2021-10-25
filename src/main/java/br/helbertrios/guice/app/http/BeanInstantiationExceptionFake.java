package br.helbertrios.guice.app.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class BeanInstantiationExceptionFake extends FatalBeanExceptionFake {

    private final Class<?> beanClass;
    private Constructor<?> constructor;
    private Method constructingMethod;

    public BeanInstantiationExceptionFake(Class<?> beanClass, String msg) {
        this(beanClass, msg, null);
    }

    public BeanInstantiationExceptionFake(Class<?> beanClass, String msg, Throwable cause) {
        super("Failed to instantiate [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    public BeanInstantiationExceptionFake(Constructor<?> constructor, String msg, Throwable cause) {
        super("Failed to instantiate [" + constructor.getDeclaringClass().getName() + "]: " + msg, cause);
        this.beanClass = constructor.getDeclaringClass();
        this.constructor = constructor;
    }

    public BeanInstantiationExceptionFake(Method constructingMethod, String msg, Throwable cause) {
        super("Failed to instantiate [" + constructingMethod.getReturnType().getName() + "]: " + msg, cause);
        this.beanClass = constructingMethod.getReturnType();
        this.constructingMethod = constructingMethod;
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public Constructor<?> getConstructor() {
        return this.constructor;
    }

    public Method getConstructingMethod() {
        return this.constructingMethod;
    }
}
