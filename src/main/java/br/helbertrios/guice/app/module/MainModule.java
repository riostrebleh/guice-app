package br.helbertrios.guice.app.module;

import br.helbertrios.guice.app.bean.*;
import br.helbertrios.guice.app.dao.DAO;
import br.helbertrios.guice.app.dao.DAOInjector;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import br.helbertrios.guice.app.provider.ProviderRequestUser;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(SpellChecker.class).to(SpellCheckerImpl.class);
        bind(ProviderRequestInfo.class);
        bind(RequestInfo.class).toProvider(ProviderRequestInfo.class).in(RequestScoped.class);
        bind(RequestUser.class).toProvider(ProviderRequestUser.class);
        bind(Feature.class).in(RequestScoped.class);
        bind(DAO.class).in(RequestScoped.class);
        bind(Simple.class).in(RequestScoped.class);
        requestStaticInjection(DAOInjector.class);

    }

}
