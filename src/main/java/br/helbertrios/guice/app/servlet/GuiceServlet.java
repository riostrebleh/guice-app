package br.helbertrios.guice.app.servlet;

import br.helbertrios.guice.app.bean.Simple;
import br.helbertrios.guice.app.provider.ProviderRequestInfo;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Singleton
public class GuiceServlet extends HttpServlet {

   @Inject
   private Simple simple;

    @Inject
    private Injector injector;

    @Inject
   private com.google.inject.Provider<ProviderRequestInfo> scope;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        ProviderRequestInfo providerRequestInfo = scope.get();
        PrintWriter writer = resp.getWriter();
        writer.println("<html><title>Guice Application</title><body>");
        writer.println("<h1>Servlet Guice</h1>");
        writer.println("<h3>"+simple.getCode()+"</h3>");
        writer.println("<h3> User: "+providerRequestInfo.get().getRequestUser().getName()+"</h3>");
        writer.println("</body></html>");
    }



}
