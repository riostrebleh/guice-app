package br.helbertrios.guice.app.http;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class RequestDispatcherFake implements RequestDispatcher {

    private final Log logger = LogFactory.getLog(getClass());

    private final String resource;


    /**
     * Create a new MockRequestDispatcher for the given resource.
     *
     * @param resource the server resource to dispatch to, located at a
     *                 particular path or given by a particular name
     */
    public RequestDispatcherFake(String resource) {
        this.resource = resource;
    }


    @Override
    public void forward(ServletRequest request, ServletResponse response) {
        if (response.isCommitted()) {
            throw new IllegalStateException("Cannot perform forward - response is already committed");
        }
        getMockHttpServletResponse(response).setForwardedUrl(this.resource);
        if (logger.isDebugEnabled()) {
            logger.debug("MockRequestDispatcher: forwarding to [" + this.resource + "]");
        }
    }

    @Override
    public void include(ServletRequest request, ServletResponse response) {
        getMockHttpServletResponse(response).addIncludedUrl(this.resource);
        if (logger.isDebugEnabled()) {
            logger.debug("MockRequestDispatcher: including [" + this.resource + "]");
        }
    }

    /**
     * Obtain the underlying {@link HttpServletResponseFake}, unwrapping
     * {@link HttpServletResponseWrapper} decorators if necessary.
     */
    protected HttpServletResponseFake getMockHttpServletResponse(ServletResponse response) {
        if (response instanceof HttpServletResponseFake) {
            return (HttpServletResponseFake) response;
        }
        if (response instanceof HttpServletResponseWrapper) {
            return getMockHttpServletResponse(((HttpServletResponseWrapper) response).getResponse());
        }
        throw new IllegalArgumentException("MockRequestDispatcher requires MockHttpServletResponse");
    }
}
