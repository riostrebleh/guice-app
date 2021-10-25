package br.helbertrios.guice.app.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

abstract class StreamUtilsFake {
    private static final byte[] EMPTY_CONTENT = new byte[0];

    public static InputStream emptyInput() {
        return new ByteArrayInputStream(EMPTY_CONTENT);
    }
}
