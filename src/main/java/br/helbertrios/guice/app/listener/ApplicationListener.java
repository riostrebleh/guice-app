package br.helbertrios.guice.app.listener;

import br.helbertrios.guice.app.module.ApplicationServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ApplicationServletModule());
    }
}
