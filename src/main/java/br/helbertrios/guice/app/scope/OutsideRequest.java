package br.helbertrios.guice.app.scope;


import com.google.common.collect.ImmutableSet;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.servlet.RequestParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class OutsideRequest {

    private OutsideRequest(){}

    private static final ThreadLocal<OutsideContext> outsideRequestScopeCtx = new ThreadLocal<OutsideContext>();

    enum NullObject { INSTANCE }

    private static final Scope OUTSIDE_REQUEST = new RequestOutsideScope();


    private static class RequestOutsideScope implements Scope {

        @Override
        public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
            return new Provider<T>() {

                private final ImmutableSet<Key<?>> REQUEST_CONTEXT_KEYS = ImmutableSet.of(
                        Key.get(HttpServletRequest.class),
                        Key.get(HttpServletResponse.class),
                        new Key<Map<String, String[]>>(RequestParameters.class) {});


                @Override
                public T get() {
                    return null;
                }
            };
        }
    }

    private static class OutsideContext {
    }
}
