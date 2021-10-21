package br.helbertrios.guice.app.module;

import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.bean.RequestUser;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import br.helbertrios.guice.app.provider.ProviderRequestUser;
import br.helbertrios.guice.app.scope.OutsideRequestScoped;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;

public class ApplicationOutsideRequestModule extends AbstractModule {

    @Override
    protected void configure() {
        bindScope(RequestScoped.class, OutsideRequestScoped.REQUEST);
        bind(RequestInfo.class).toProvider(ProviderRequestInfo.class).in(RequestScoped.class);
        bind(RequestUser.class).toProvider(ProviderRequestUser.class);
    }

}
