package br.helbertrios.guice.app.filter;

import br.helbertrios.guice.app.bean.RequestAction;
import br.helbertrios.guice.app.bean.RequestInfo;
import br.helbertrios.guice.app.bean.RequestUser;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class GuiceRequestFilter implements Filter {

    private static int sequencial = 0;
    private static final Map<String, String> tokens = new ConcurrentHashMap<>();

    @Inject
    private Injector injector;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        initRequest(request, writer);
        chain.doFilter(request, response);
    }

    protected RequestInfo initRequest(ServletRequest req, PrintWriter writer) {
        if (validRequest(req, writer)) {
            RequestUser requestUser = new RequestUser();
            requestUser.setId(req.getParameter("id"));
            requestUser.setLogin(req.getParameter("login"));
            requestUser.setName(req.getParameter("name"));

            RequestAction requestAction = new RequestAction();
            requestAction.setClassName(req.getParameter("className"));
            requestAction.setMehodName(req.getParameter("mehodName"));

            String tokenID = tokens.get(requestUser.getId());
            if (tokenID == null) {
                tokenID = UUID.randomUUID().toString();
                tokens.put(requestUser.getId(), tokenID);
            }

            ProviderRequestInfo providerRequestInfo = injector.getInstance(ProviderRequestInfo.class);
            RequestInfo requestInfo = new RequestInfo(requestUser, requestAction, tokenID, ++sequencial);
            providerRequestInfo.set(requestInfo);
            return requestInfo;
        } else {
            RequestUser requestUser = new RequestUser();
            requestUser.setId("0");
            requestUser.setLogin("Anonymous");
            requestUser.setName("Anonymous");

            RequestAction requestAction = new RequestAction();
            requestAction.setClassName("Simple");
            requestAction.setMehodName("getCode");

            String tokenID = tokens.get(requestUser.getId());
            if (tokenID == null) {
                tokenID = UUID.randomUUID().toString();
                tokens.put(requestUser.getId(), tokenID);
            }
            ProviderRequestInfo providerRequestInfo = injector.getInstance(ProviderRequestInfo.class);
            RequestInfo requestInfo = new RequestInfo(requestUser, requestAction, tokenID, ++sequencial);
            providerRequestInfo.set(requestInfo);
        }
        return null;
    }

    protected boolean validRequest(ServletRequest req, PrintWriter writer) {
        boolean result = true;
        if (req.getParameter("id") == null) {
            writer.println("<BR><span>Parameter id is required.</span>");
            result = false;
        }

        if (req.getParameter("login") == null) {
            writer.println("<BR><span>Parameter login is required.</span>");
            result = false;
        }


        if (req.getParameter("className") == null) {
            writer.println("<BR><span>Parameter className is required.</span>");
            result = false;
        }

        if (req.getParameter("className") == null) {
            writer.println("<BR><span>Parameter className is required.</span>");
            result = false;
        }
        return result;
    }

    @Override
    public void destroy() {

    }
}
