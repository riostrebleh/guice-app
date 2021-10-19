package br.helbertrios.guice.app.provider;

import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.bean.RequestUser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;


@RequestScoped
public class ProviderRequestUser implements Provider<RequestUser> {

    @Inject
    private Provider<RequestInfo> requestInfoProvider;

    @Override
    public RequestUser get() {
        RequestInfo requestInfo = requestInfoProvider.get();
        return requestInfo.getRequestUser();
    }

}
