package br.helbertrios.guice.app.dao;

import br.helbertrios.guice.app.bean.RequestInfo;
import com.google.inject.Inject;

public class DAO implements AutoCloseable {

    private RequestInfo requestInfo;

    @Inject
    public DAO(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
        if (this.requestInfo != null) {
            this.requestInfo.addDAO(this);
        }
    }

    @Override
    public void close() throws Exception {

    }

    public String getMessage() {
        return "Inside DAO";
    }
}
