package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;

class ReqFakeRequest {


    private final String json;
    private final RequestAction acao;

    public ReqFakeRequest(String json, RequestAction acao) {
        this.json = json;
        this.acao = acao;
    }



}
