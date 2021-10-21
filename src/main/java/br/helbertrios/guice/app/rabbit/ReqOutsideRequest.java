package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import com.google.inject.Injector;

class ReqOutsideRequest {

    private final Injector injector;
    private final String json;
    private final RequestAction acao;

    public ReqOutsideRequest(Injector injector, String json, RequestAction acao) {
        this.injector = injector;
        this.json = json;
        this.acao = acao;
    }

    public Injector getInjector() {
        return injector;
    }


    public String getJson() {
        return json;
    }

    public RequestAction getAcao() {
        return acao;
    }


}
