package br.helbertrios.guice.app.http;


import java.io.IOException;

public class NestedIOExceptionFake extends IOException {

    static {
        // Eagerly load the NestedExceptionUtils class to avoid classloader deadlock
        // issues on OSGi when calling getMessage(). Reported by Don Brown; SPR-5607.
        NestedIOExceptionFake.class.getName();
    }


    /**
     * Construct a {@code NestedIOException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public NestedIOExceptionFake(String msg) {
        super(msg);
    }

    /**
     * Construct a {@code NestedIOException} with the specified detail message
     * and nested exception.
     *
     * @param msg   the detail message
     * @param cause the nested exception
     */
    public NestedIOExceptionFake(String msg, Throwable cause) {
        super(msg, cause);
    }


    /**
     * Return the detail message, including the message from the nested exception
     * if there is one.
     */
    @Override
    public String getMessage() {
        return NestedExceptionUtilsFake.buildMessage(super.getMessage(), getCause());
    }
}
