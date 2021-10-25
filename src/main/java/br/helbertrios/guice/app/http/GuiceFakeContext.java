package br.helbertrios.guice.app.http;

import com.google.inject.servlet.GuiceFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class GuiceFakeContext {


    public static void open()  {


        HttpServletRequestFake request = new HttpServletRequestFake();
        HttpServletResponseFake response = new HttpServletResponseFake();

        try {
            Class<?>[] innerClasses = GuiceFilter.class.getDeclaredClasses();
            Optional<Class<?>> optional = Arrays.stream(innerClasses)
                    .filter(x -> "com.google.inject.servlet.GuiceFilter$Context".equals(x.getName()))
                    .findFirst();

            if (optional.isPresent()) {
                Class<?> clazz = optional.get();
                System.out.println("Class: " + clazz.getName());
                Constructor<?> constructor = clazz.getDeclaredConstructor(HttpServletRequest.class, HttpServletRequest.class, HttpServletResponse.class);
                constructor.setAccessible(true);
                Object context = constructor.newInstance(request, request, response);

                Method method = clazz.getDeclaredMethod("open");
                method.setAccessible(true);
                method.invoke(context);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


