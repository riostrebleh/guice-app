package br.helbertrios.guice.app.http;




class BeansExceptionFake extends NestedRuntimeExceptionFake {

    public BeansExceptionFake(String msg) {
        super(msg);
    }

    public BeansExceptionFake(String msg, Throwable cause) {
        super(msg, cause);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof BeansExceptionFake)) {
            return false;
        } else {
            BeansExceptionFake otherBe = (BeansExceptionFake) other;
            return this.getMessage().equals(otherBe.getMessage()) && ObjectUtilsFake.nullSafeEquals(this.getCause(), otherBe.getCause());
        }
    }

    public int hashCode() {
        return this.getMessage().hashCode();
    }
}
