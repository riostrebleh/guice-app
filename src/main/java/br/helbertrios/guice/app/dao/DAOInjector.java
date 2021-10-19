package br.helbertrios.guice.app.dao;

import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.bean.RequestUser;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DAOInjector {

    @Inject
    private static Provider<RequestUser> user;

    @Inject
    private static Provider<RequestInfo> request;

}
