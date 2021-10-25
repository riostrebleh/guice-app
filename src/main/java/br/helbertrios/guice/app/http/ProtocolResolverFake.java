package br.helbertrios.guice.app.http;


interface ProtocolResolverFake {
    ResourceFake resolve(String location, ResourceLoaderFake resourceLoader);
}
