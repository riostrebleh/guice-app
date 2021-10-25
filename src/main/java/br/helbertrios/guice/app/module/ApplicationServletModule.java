package br.helbertrios.guice.app.module;

import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.bean.RequestUser;
import br.helbertrios.guice.app.filter.GuiceRequestFilter;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import br.helbertrios.guice.app.provider.ProviderRequestUser;
import br.helbertrios.guice.app.servlet.GuiceServlet;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;


public class ApplicationServletModule extends ServletModule {
    @Override
    protected void configureServlets() {

        serve("/*").with(GuiceServlet.class);
        filter("/*").through(GuiceRequestFilter.class);

        bind(RequestInfo.class).toProvider(ProviderRequestInfo.class).in(RequestScoped.class);
        bind(RequestUser.class).toProvider(ProviderRequestUser.class);

        //install(new MainModule());
        //filter("/").through(RequestFilter.class);;
    }
}
