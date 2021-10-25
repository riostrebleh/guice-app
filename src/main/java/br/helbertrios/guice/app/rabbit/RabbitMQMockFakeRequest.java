package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.http.GuiceFakeContext;
import br.helbertrios.guice.app.module.ApplicationServletModule;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RabbitMQMockFakeRequest {

    private final RabbitMQConsumerMockFakeRequest consumerMock;

    public RabbitMQMockFakeRequest() {

        Timer timer = new Timer(true);
        consumerMock = new RabbitMQConsumerMockFakeRequest();
        Date hoje = new GregorianCalendar().getTime();
        timer.schedule(consumerMock, hoje, 10000);
    }

    public void put(String json, RequestAction acao) {
        consumerMock.put(json, acao);
    }

}

class RabbitMQConsumerMockFakeRequest extends TimerTask {

    private static final ConcurrentLinkedQueue<ReqFakeRequest> execs = new ConcurrentLinkedQueue<>();

    public void put(String json, RequestAction acao) {
        execs.add(new ReqFakeRequest(json, acao));
    }

    @Override
    public void run() {
        try {
            if (execs.isEmpty()) {
                return;
            }


            ReqFakeRequest req = execs.poll();
            GuiceFakeContext.open();
            Injector injector = Guice.createInjector(new ApplicationServletModule());
            ProviderRequestInfo providerRequestInfo = injector.getInstance(ProviderRequestInfo.class);
            RequestInfo requestInfo = providerRequestInfo.get();

            if (requestInfo == null) {
                System.out.println("requestInfo == null");
            } else {
                System.out.println("requestInfo != null");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
