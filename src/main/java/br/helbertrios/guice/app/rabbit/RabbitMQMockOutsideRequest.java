package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.module.ApplicationOutsideRequestModule;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RabbitMQMockOutsideRequest {

    private final RabbitMQConsumerMockOutsideRequest consumerMock;

    public RabbitMQMockOutsideRequest() {
        Timer timer = new Timer(true);
        consumerMock = new RabbitMQConsumerMockOutsideRequest();
        Date hoje = new GregorianCalendar().getTime();
        timer.schedule(consumerMock, hoje, 10000);
    }

    public void put(Injector injector, String json, RequestAction acao) {
        consumerMock.put(injector, json, acao);
    }

}

class RabbitMQConsumerMockOutsideRequest extends TimerTask {
    private static final ConcurrentLinkedQueue<ReqOutsideRequest> execs = new ConcurrentLinkedQueue<>();

    public void put(Injector injector, String json, RequestAction acao) {
        execs.add(new ReqOutsideRequest(injector, json, acao));
    }

    @Override
    public void run() {
        try {
            if (execs.isEmpty()) {
                return;
            }

            ReqOutsideRequest req = execs.poll();
            Injector injector = Guice.createInjector(new ApplicationOutsideRequestModule());
            ProviderRequestInfo providerRequestInfo = injector.getInstance(ProviderRequestInfo.class);
            RequestInfo requestInfo = providerRequestInfo.get();

            if (requestInfo == null) {
                System.out.println("requestInfo == null -> "+req);
            } else {
                System.out.println("requestInfo != null ->"+req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
