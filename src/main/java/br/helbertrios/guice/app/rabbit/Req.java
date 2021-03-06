package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoper;

class Req {

    private final Injector injector;
    private final String json;
    private final RequestAction acao;
    private final RequestScoper requestScoper;

    public Req(Injector injector, String json, RequestAction acao, RequestScoper requestScoper) {
        this.injector = injector;
        this.json = json;
        this.acao = acao;
        this.requestScoper = requestScoper;
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

    public RequestScoper getRequestScoper() {
        return requestScoper;
    }

}
