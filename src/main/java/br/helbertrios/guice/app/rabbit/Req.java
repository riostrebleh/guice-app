package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoper;

public class Req {

    private Injector injector;
    private String json;
    private RequestAction acao;
    private RequestScoper requestScoper;

    public Req(Injector injector,  String json, RequestAction acao, RequestScoper requestScoper) {
        this.injector = injector;
        this.json = json;
        this.acao = acao;
        this.requestScoper = requestScoper ;
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
