package br.helbertrios.guice.app.http;

class InvalidMimeTypeExceptionFake extends IllegalArgumentException {


    private final String mimeType;


    /**
     * Create a new InvalidContentTypeException for the given content type.
     *
     * @param mimeType the offending media type
     * @param message  a detail message indicating the invalid part
     */
    public InvalidMimeTypeExceptionFake(String mimeType, String message) {
        super("Invalid mime type \"" + mimeType + "\": " + message);
        this.mimeType = mimeType;
    }


    /**
     * Return the offending content type.
     */
    public String getMimeType() {
        return this.mimeType;
    }
}
