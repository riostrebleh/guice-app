package br.helbertrios.guice.app.arch;

import br.helbertrios.guice.app.bean.Feature;
import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.servlet.ServletContext;


public class ActionExecutor {

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

        Feature feature = injector.getInstance(Feature.class);
        return feature.getMessage();
    }

}
