package br.helbertrios.guice.app.http;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class BeanUtilsFake {
    public static <T> T instantiateClass(Class<T> clazz) throws BeanInstantiationExceptionFake {
        if (clazz.isInterface()) {
            throw new BeanInstantiationExceptionFake(clazz, "Specified class is an interface");
        } else {
            try {
                return instantiateClass(clazz.getDeclaredConstructor());
            } catch (NoSuchMethodException var2) {
                throw new BeanInstantiationExceptionFake(clazz, "No default constructor found", var2);
            }
        }
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationExceptionFake {

        try {
            ReflectionUtilsFake.makeAccessible(ctor);
            return ctor.newInstance(args);
        } catch (InstantiationException var3) {
            throw new BeanInstantiationExceptionFake(ctor, "Is it an abstract class?", var3);
        } catch (IllegalAccessException var4) {
            throw new BeanInstantiationExceptionFake(ctor, "Is the constructor accessible?", var4);
        } catch (IllegalArgumentException var5) {
            throw new BeanInstantiationExceptionFake(ctor, "Illegal arguments for constructor", var5);
        } catch (InvocationTargetException var6) {
            throw new BeanInstantiationExceptionFake(ctor, "Constructor threw exception", var6.getTargetException());
        }
    }
}
