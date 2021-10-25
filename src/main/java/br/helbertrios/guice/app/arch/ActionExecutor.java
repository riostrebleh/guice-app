package br.helbertrios.guice.app.arch;

import br.helbertrios.guice.app.bean.Feature;
import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.rabbit.RabbitMQMock;
import br.helbertrios.guice.app.rabbit.RabbitMQMockFakeRequest;
import br.helbertrios.guice.app.rabbit.RabbitMQMockOutsideRequest;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoper;
import com.google.inject.servlet.ServletScopes;

import javax.servlet.ServletContext;


public class ActionExecutor {

    private static final RabbitMQMock rabbitMQMock = new RabbitMQMock();
    private static final RabbitMQMockOutsideRequest rabbitMQMockOutsideRequest = new RabbitMQMockOutsideRequest();
    private static final RabbitMQMockFakeRequest rabbitMQMockFakeRequest = new RabbitMQMockFakeRequest();
    private final Injector injector;
    private final ServletContext context;

    @Inject
    public ActionExecutor(Injector injector, ServletContext context) {
        this.injector = injector;
        this.context = context;

    }

    public Object executaAcao(String className, String methodName) {

        RequestInfo requestInfo = injector.getInstance(RequestInfo.class);
        RequestAction requestAction = new RequestAction();
        requestAction.setClassName(className);
        requestAction.setMehodName(methodName);
        requestInfo.setRequestAction(requestAction);

        boolean isLongRunning = true;

        if (isLongRunning) {
            // final RequestScoper requestScoper = ServletScopes.transferRequest();

            rabbitMQMockFakeRequest.put("{}", requestAction);
            // rabbitMQMock.put(injector, "{}", requestAction, requestScoper);

            // rabbitMQMockOutsideRequest.put(injector, "{}", requestAction);


            //             TesteCallable testeCallable = new TesteCallable(new Req(injector, "{}", requestAction, requestScoper));
            //            ServletScopes.transferRequest(testeCallable);
        }

        Feature feature = injector.getInstance(Feature.class);
        return feature.getMessage();
    }

}
