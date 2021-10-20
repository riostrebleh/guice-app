package br.helbertrios.guice.app.rabbit;

import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.module.ApplicationServletModule;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoper;

import java.rmi.server.ExportException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RabbitMQMock {

    private RabbitMQConsumerMock consumerMock;

    public RabbitMQMock() {
        Timer timer = new Timer(true);
        consumerMock = new RabbitMQConsumerMock();
        Date hoje = new GregorianCalendar().getTime();
        timer.schedule(consumerMock, hoje, 10000);
    }

    public void put (Injector injector, String json, RequestAction acao, RequestScoper requestScoper) {
        consumerMock.put(injector, json, acao, requestScoper);
    }

}

class RabbitMQConsumerMock extends TimerTask {
    private static ConcurrentLinkedQueue<Req> execs = new ConcurrentLinkedQueue<>();

    public void put(Injector injector, String json, RequestAction acao, RequestScoper requestScoper) {
        execs.add(new Req(injector, json, acao, requestScoper));
    }

    @Override
    public void run() {
        try {
            if (execs.isEmpty()) {
                return;
            }

            Req req = execs.poll();
            req.getRequestScoper().open();
            Injector injector = req.getInjector();
            ProviderRequestInfo providerRequestInfo = injector.getInstance(ProviderRequestInfo.class);
            RequestInfo requestInfo = providerRequestInfo.get();



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
