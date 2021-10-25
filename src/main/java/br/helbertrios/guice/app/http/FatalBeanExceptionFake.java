package br.helbertrios.guice.app.http;


class FatalBeanExceptionFake extends BeansExceptionFake {
    public FatalBeanExceptionFake(String msg) {
        super(msg);
    }

    public FatalBeanExceptionFake(String msg, Throwable cause) {
        super(msg, cause);
    }
}
