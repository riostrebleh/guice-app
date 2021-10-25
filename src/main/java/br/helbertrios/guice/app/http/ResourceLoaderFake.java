package br.helbertrios.guice.app.http;

interface ResourceLoaderFake {

    String CLASSPATH_URL_PREFIX = "classpath:";

    ResourceFake getResource(String var1);

    ClassLoader getClassLoader();
}
