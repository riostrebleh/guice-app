package br.helbertrios.guice.app.http;

class LogConfigurationExceptionFake extends RuntimeException {

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 8486587136871052495L;
    /**
     * The underlying cause of this exception.
     */
    protected Throwable cause = null;

    /**
     * Construct a new exception with <code>null</code> as its detail message.
     */
    public LogConfigurationExceptionFake() {
        super();
    }

    /**
     * Construct a new exception with the specified detail message.
     *
     * @param message The detail message
     */
    public LogConfigurationExceptionFake(String message) {
        super(message);
    }

    /**
     * Construct a new exception with the specified cause and a derived
     * detail message.
     *
     * @param cause The underlying cause
     */
    public LogConfigurationExceptionFake(Throwable cause) {
        this(cause == null ? null : cause.toString(), cause);
    }

    /**
     * Construct a new exception with the specified detail message and cause.
     *
     * @param message The detail message
     * @param cause   The underlying cause
     */
    public LogConfigurationExceptionFake(String message, Throwable cause) {
        super(message + " (Caused by " + cause + ")");
        this.cause = cause; // Two-argument version requires JDK 1.4 or later
    }

    /**
     * Return the underlying cause of this exception (if any).
     */
    public Throwable getCause() {
        return this.cause;
    }
}
