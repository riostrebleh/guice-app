package br.helbertrios.guice.app.arch;

import br.helbertrios.guice.app.bean.Feature;
import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import br.helbertrios.guice.app.rabbit.RabbitMQMock;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoper;
import com.google.inject.servlet.ServletScopes;

import javax.servlet.ServletContext;


public class ActionExecutor {

    private final Injector injector;
    private final ServletContext context;

    private static RabbitMQMock rabbitMQMock = new RabbitMQMock();

    @Inject
    public ActionExecutor(Injector injector, ServletContext context) {
        this.injector = injector;
        this.context = context;

    }

    public Object executaAcao(String className, String methodName) {
        final RequestScoper requestScoper = ServletScopes.transferRequest();
        RequestInfo requestInfo = injector.getInstance(RequestInfo.class);
        RequestAction requestAction = new RequestAction();
        requestAction.setClassName(className);
        requestAction.setMehodName(methodName);
        requestInfo.setRequestAction(requestAction);

        boolean isLongRunning = true;

        if(isLongRunning){

            rabbitMQMock.put(injector, "{}", requestAction, requestScoper);
        }

        Feature feature = injector.getInstance(Feature.class);
        return feature.getMessage();
    }

}
