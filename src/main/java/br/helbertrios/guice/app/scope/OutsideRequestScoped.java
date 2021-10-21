package br.helbertrios.guice.app.scope;


import com.google.common.collect.Maps;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoper;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class OutsideRequestScoped implements com.google.inject.Scope {

    public static final com.google.inject.Scope REQUEST = new OutsideRequestScoped();
    static final ThreadLocal<Context> localContext = new ThreadLocal<Context>();

    @Override
    public <T> Provider<T> scope(Key<T> key, Provider<T> creator) {
        return new Provider<T>() {
            @Override
            public T get() {

                OutsideRequestScoped.Context context = localContext.get();
                if (null == context) {
                    context = new OutsideRequestScoped.Context();
                    context.open();
                }

                if (null != context) {
                    @SuppressWarnings("unchecked")
                    T t = (T) context.map.get(key);

                    // Accounts for @Nullable providers.
                    if (NullObject.INSTANCE == t) {
                        return null;
                    }

                    if (t == null) {
                        t = creator.get();
                        if (!Scopes.isCircularProxy(t)) {
                            // Store a sentinel for provider-given null values.
                            context.map.put(key, t != null ? t : NullObject.INSTANCE);
                        }
                    }

                    return t;
                }
                return null;
            }
        };
    }

    enum NullObject {INSTANCE}

    private static class Context implements RequestScoper {
        final Map<Key, Object> map = Maps.newHashMap();

        // Synchronized to prevent two threads from using the same request
        // scope concurrently.
        final Lock lock = new ReentrantLock();

        @Override
        public CloseableScope open() {
            lock.lock();
            final Context previous = localContext.get();
            localContext.set(this);
            return new CloseableScope() {
                @Override
                public void close() {
                    localContext.set(previous);
                    lock.unlock();
                }
            };
        }
    }
}
