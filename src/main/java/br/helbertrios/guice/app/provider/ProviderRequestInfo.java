package br.helbertrios.guice.app.provider;

import br.helbertrios.guice.app.bean.RequestInfo;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class ProviderRequestInfo implements Provider<RequestInfo> {

    private RequestInfo requestInfo;

    @Inject
    public ProviderRequestInfo() {
    }

    @Override
    public RequestInfo get() {
        return requestInfo;
    }

    public void set(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}

