package br.helbertrios.guice.app.http;


class NestedRuntimeExceptionFake extends RuntimeException {
    private static final long serialVersionUID = 5439915454935047936L;

    static {
        NestedExceptionUtilsFake.class.getName();
    }

    public NestedRuntimeExceptionFake(String msg) {
        super(msg);
    }

    public NestedRuntimeExceptionFake(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getMessage() {
        return NestedExceptionUtilsFake.buildMessage(super.getMessage(), this.getCause());
    }

    public Throwable getRootCause() {
        return NestedExceptionUtilsFake.getRootCause(this);
    }

    public Throwable getMostSpecificCause() {
        Throwable rootCause = this.getRootCause();
        return rootCause != null ? rootCause : this;
    }

    public boolean contains(Class<?> exType) {
        if (exType == null) {
            return false;
        } else if (exType.isInstance(this)) {
            return true;
        } else {
            Throwable cause = this.getCause();
            if (cause == this) {
                return false;
            } else if (cause instanceof NestedRuntimeExceptionFake) {
                return ((NestedRuntimeExceptionFake) cause).contains(exType);
            } else {
                while (cause != null) {
                    if (exType.isInstance(cause)) {
                        return true;
                    }

                    if (cause.getCause() == cause) {
                        break;
                    }

                    cause = cause.getCause();
                }

                return false;
            }
        }
    }
}
