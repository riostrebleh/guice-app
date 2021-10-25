package br.helbertrios.guice.app.http;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

class DefaultResourceLoaderFake implements ResourceLoaderFake {
    private final Set<ProtocolResolverFake> protocolResolvers = new LinkedHashSet<ProtocolResolverFake>(4);
    private ClassLoader classLoader;


    /**
     * Create a new DefaultResourceLoader.
     * <p>ClassLoader access will happen using the thread context class loader
     * at the time of this ResourceLoader's initialization.
     *
     * @see java.lang.Thread#getContextClassLoader()
     */
    public DefaultResourceLoaderFake() {
        this.classLoader = ClassUtilsFake.getDefaultClassLoader();
    }

    /**
     * Create a new DefaultResourceLoader.
     *
     * @param classLoader the ClassLoader to load class path resources with, or {@code null}
     *                    for using the thread context class loader at the time of actual resource access
     */
    public DefaultResourceLoaderFake(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Return the ClassLoader to load class path resources with.
     * <p>Will get passed to ClassPathResource's constructor for all
     * ClassPathResource objects created by this resource loader.
     */
    @Override
    public ClassLoader getClassLoader() {
        return (this.classLoader != null ? this.classLoader : ClassUtilsFake.getDefaultClassLoader());
    }

    /**
     * Specify the ClassLoader to load class path resources with, or {@code null}
     * for using the thread context class loader at the time of actual resource access.
     * <p>The default is that ClassLoader access will happen using the thread context
     * class loader at the time of this ResourceLoader's initialization.
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Register the given resolver with this resource loader, allowing for
     * additional protocols to be handled.
     * <p>Any such resolver will be invoked ahead of this loader's standard
     * resolution rules. It may therefore also override any default rules.
     *
     * @see #getProtocolResolvers()
     * @since 4.3
     */
    public void addProtocolResolver(ProtocolResolverFake resolver) {
        this.protocolResolvers.add(resolver);
    }

    /**
     * Return the collection of currently registered protocol resolvers,
     * allowing for introspection as well as modification.
     *
     * @since 4.3
     */
    public Collection<ProtocolResolverFake> getProtocolResolvers() {
        return this.protocolResolvers;
    }


    @Override
    public ResourceFake getResource(String location) {


        for (ProtocolResolverFake protocolResolver : this.protocolResolvers) {
            ResourceFake resource = protocolResolver.resolve(location, this);
            if (resource != null) {
                return resource;
            }
        }

        if (location.startsWith("/")) {
            return getResourceByPath(location);
        } else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResourceFake(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
        } else {
            try {
                // Try to parse the location as a URL...
                URL url = new URL(location);
                return new UrlResourceFake(url);
            } catch (MalformedURLException ex) {
                // No URL -> resolve as resource path.
                return getResourceByPath(location);
            }
        }
    }

    /**
     * Return a Resource handle for the resource at the given path.
     * <p>The default implementation supports class path locations. This should
     * be appropriate for standalone implementations but can be overridden,
     * e.g. for implementations targeted at a Servlet container.
     *
     * @param path the path to the resource
     * @return the corresponding Resource handle
     */
    protected ResourceFake getResourceByPath(String path) {
        return new DefaultResourceLoaderFake.ClassPathContextResource(path, getClassLoader());
    }


    /**
     * ClassPathResource that explicitly expresses a context-relative path
     * through implementing the ContextResource interface.
     */
    protected static class ClassPathContextResource extends ClassPathResourceFake implements ContextResourceFake {

        public ClassPathContextResource(String path, ClassLoader classLoader) {
            super(path, classLoader);
        }

        @Override
        public String getPathWithinContext() {
            return getPath();
        }

        @Override
        public ResourceFake createRelative(String relativePath) {
            String pathToUse = StringUtilsFake.applyRelativePath(getPath(), relativePath);
            return new DefaultResourceLoaderFake.ClassPathContextResource(pathToUse, getClassLoader());
        }
    }
}
